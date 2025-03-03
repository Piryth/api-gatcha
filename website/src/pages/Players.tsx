'use client';

import * as React from 'react';
import {
  ColumnDef,
  ColumnFiltersState,
  SortingState,
  VisibilityState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from '@tanstack/react-table';
import { ArrowUpCircleIcon, Copy, MoreHorizontal, Plus, Trash, X } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { toast } from 'sonner';
import { z } from 'zod';
import { newPlayerSchema } from '@/lib/zod';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

export type Player = {
  id: string;
  name: string;
  level: number;
  exp: number;
  curr_exp: number;
};

export function Players() {
  const [players, setPlayers] = React.useState<Player[]>([]);
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = React.useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = React.useState({});

  const form = useForm<z.infer<typeof newPlayerSchema>>({
    resolver: zodResolver(newPlayerSchema),
    defaultValues: {
      name: '',
      level: 1,
      exp: 50,
      curr_exp: 0,
      monsters: [],
    },
  });

  React.useEffect(() => {
    fetchPlayers();
  }, []);

  async function fetchPlayers() {
    try {
      const response = await fetch('http://localhost:8081/api/players/list');
      const data = await response.json();
      setPlayers(data);
    } catch (error) {
      toast.error('Erreur lors de la récupération des joueurs :', error);
    }
  }

  async function deletePlayer(playerId: String) {
    try {
      const response = await fetch(`http://localhost:8081/api/players/${playerId}`, {
        method: 'delete',
      });
      const data = await response.text();
      toast.success(data);
      setPlayers(players.filter((p) => p.id != playerId));
    } catch (error) {
      toast.error("Erreur lors de la suppression d'un joueur :", error);
    }
  }

  async function levelUp(playerId: String) {
    try {
      await fetch(`http://localhost:8081/api/players/${playerId}/levelUp`);
      await fetchPlayers();
      toast.success('Playel level increased sucessfuly');
    } catch (error) {
      toast.error("Erreur lors de la suppression d'un joueur :", error);
    }
  }

  async function createNewPlayer(values: z.infer<typeof newPlayerSchema>) {
    try {
      await fetch(`http://localhost:8081/api/players/new`, {
        method: 'post',
        body: JSON.stringify(values),
        headers: {
          'Content-Type': 'application/json',
        },
      });
      toast.success('Utilisateur ajouté avec succès');
      fetchPlayers();
    } catch (error) {
      toast.error("Erreur lors de l'ajout d'un joueur :", error);
    }
  }

  const columns: ColumnDef<Player>[] = [
    {
      accessorKey: 'name',
      header: 'Nom',
      cell: ({ row }) => <div>{row.getValue('name')}</div>,
    },
    {
      accessorKey: 'level',
      header: 'Niveau',
      cell: ({ row }) => <div className='text-left'>{row.getValue('level')}</div>,
    },
    {
      accessorKey: 'exp',
      header: 'Expérience',
      cell: ({ row }) => <div className='text-left'>{row.getValue('exp')}</div>,
    },
    {
      accessorKey: 'curr_exp',
      header: 'Exp actuelle',
      cell: ({ row }) => <div className='text-left'>{row.getValue('curr_exp')}</div>,
    },
    {
      id: 'actions',
      enableHiding: false,
      header: 'Actions',
      cell: ({ row }) => {
        const player = row.original;

        return (
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='ghost' className='h-8 w-8 p-0'>
                <span className='sr-only'>Ouvrir le menu</span>
                <MoreHorizontal />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align='start'>
              <DropdownMenuItem className='flex gap-4' onClick={() => navigator.clipboard.writeText(player.id)}>
                <Copy className='w-4 h-4' /> Copier l'ID du joueur
              </DropdownMenuItem>
              <DropdownMenuItem className='flex gap-4' onClick={() => levelUp(player.id)}>
                <ArrowUpCircleIcon className='w-4 h-4' /> Améliorer le joueur
              </DropdownMenuItem>
              <DropdownMenuItem className='flex gap-4 text-red-700 hover:!text-red-700' onClick={() => deletePlayer(player.id)}>
                <Trash className='w-4 h-4' /> Supprimer le joueur
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        );
      },
    },
  ];

  const table = useReactTable({
    data: players,
    columns,
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    state: {
      sorting,
      columnFilters,
      columnVisibility,
      rowSelection,
    },
  });

  return (
    <div className='w-full p-16'>
      <h1 className='text-4xl -'>Listes des joueurs</h1>
      <div className='flex items-center justify-between py-4'>
        <Input
          placeholder='Rechercher un joueur...'
          value={(table.getColumn('name')?.getFilterValue() as string) ?? ''}
          onChange={(event) => table.getColumn('name')?.setFilterValue(event.target.value)}
          className='max-w-sm'
        />

        <Dialog>
          <DialogTrigger asChild>
            <Button variant='outline'>
              <Plus className='w-4 h-4' />
              <span>Ajouter un joueur</span>
            </Button>
          </DialogTrigger>
          <DialogContent className='sm:max-w-[625px]'>
            <DialogHeader>
              <DialogTitle>Création d'un nouveau joueur</DialogTitle>
              <DialogDescription>Vous pouvez créer un joueur dans cette interface.</DialogDescription>
            </DialogHeader>
            <Form {...form}>
              <form onSubmit={form.handleSubmit(createNewPlayer)} className='space-y-8'>
                <FormField
                  control={form.control}
                  name='name'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nom</FormLabel>
                      <FormControl>
                        <Input placeholder='Iron man' {...field} />
                      </FormControl>
                      <FormDescription>Le nom visible de votre joueur</FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <Button type='submit'>Enregistrer</Button>
              </form>
            </Form>
          </DialogContent>
        </Dialog>
      </div>
      <div className='rounded-md border'>
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <TableHead key={header.id}>
                    {header.isPlaceholder ? null : flexRender(header.column.columnDef.header, header.getContext())}
                  </TableHead>
                ))}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>{flexRender(cell.column.columnDef.cell, cell.getContext())}</TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className='h-24 text-center'>
                  Aucun joueur trouvé.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
