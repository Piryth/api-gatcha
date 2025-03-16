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
import { Copy, MoreHorizontal, MoreVertical, Plus, RefreshCw, Trash } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
import { toast } from 'sonner';
import { axiosConfig } from '@/config/axiosConfig';
import { ElementType, ENUM_ELEMENT } from '@/types/ElementType';
import { DialogHeader, Dialog, DialogContent, DialogTitle, DialogDescription } from '@/components/ui/dialog';
import { FormField, FormItem, FormLabel, FormControl, FormMessage, Form } from '../components/ui/form';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { invocSchema } from '@/lib/zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from '../components/ui/select';
import { Player } from './Players';

export type Invoc = {
  id: string;
  name: string;
  element: ElementType;
  lootRate: number;
};

export function Invoc() {
  const [invocs, setInvocs] = React.useState<Invoc[]>([]);
  const [players, setPlayers] = React.useState<Player[]>([]);
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = React.useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = React.useState({});

  const [dialogOpen, setDialogOpen] = React.useState(false);

  const form = useForm<z.infer<typeof invocSchema>>({
    resolver: zodResolver(invocSchema),
    defaultValues: {
      playerId: '',
    },
  });
  React.useEffect(() => {
    fetchInvocs();
    fetchPlayers();
  }, []);

  async function fetchPlayers() {
    try {
      const response = await axiosConfig.get('/player-api/v1/players/list');
      const data = await response.data;
      setPlayers(data);
    } catch (error) {
      toast.error('Erreur lors de la récupération des joueurs :', error);
    }
  }

  async function fetchInvocs() {
    try {
      const response = await axiosConfig.get('/invocation-api/v1/invocations/list');
      const data = await response.data;
      setInvocs(data);
      toast.success('Invocations récupérés avec succès');
    } catch (error) {
      toast.error('Erreur lors de la récupération des invocations :', error);
    }
  }

  async function deleteInvoc() {
    try {
      await axiosConfig.delete('/invocation-api/v1/invocations/delete');
      setInvocs([]);
      toast.success('Invocations supprimés avec succès');
    } catch (error) {
      toast.error('Erreur lors de la suppression des invocations :', error);
    }
  }

  async function randomInvoc(values: z.infer<typeof invocSchema>) {
    try {
      const response = await axiosConfig.get(`/invocation-api/v1/invocations/random/${values.playerId}`);
      const monster = await response.data;
      setDialogOpen(false);
      toast.success(`Vous avez invoqué un ${monster.name}, rendez vous sur la page des monstres`);
    } catch (error) {
      toast.error("Erreur lors de l'invocation aléatoire :", error);
    }
  }

  const columns: ColumnDef<Invoc>[] = [
    {
      accessorKey: 'id',
      header: 'Id',
      cell: ({ row }) => <div>{row.getValue('id')}</div>,
    },
    {
      accessorKey: 'name',
      header: 'Nom',
      cell: ({ row }) => <div>{row.getValue('name')}</div>,
    },
    {
      accessorKey: 'element',
      header: 'Élément',
      cell: ({ row }) => {
        const elementKey = row.getValue('element') as keyof typeof ENUM_ELEMENT;
        return <div>{ENUM_ELEMENT[elementKey] || elementKey}</div>;
      },
    },
    {
      accessorKey: 'lootRate',
      header: 'Taux de loot',
      cell: ({ row }) => <div>{row.getValue('lootRate')}</div>,
    },
    {
      id: 'actions',
      enableHiding: false,
      header: 'Actions',
      cell: ({ row }) => {
        const invoc = row.original;

        return (
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='ghost' className='h-8 w-8 p-0'>
                <MoreHorizontal />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align='start'>
              <DropdownMenuItem className='flex gap-4' onClick={() => navigator.clipboard.writeText(invoc.id)}>
                <Copy className='w-4 h-4' /> Copier l'ID de l'invocation
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        );
      },
    },
  ];

  const table = useReactTable({
    data: invocs,
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
      <h1 className='text-4xl'>Listes des invocations</h1>
      <div className='flex w-full items-center justify-between py-4'>
        <div className='flex gap-4 items-center'>
          <Input
            placeholder='Rechercher une invocation...'
            value={(table.getColumn('name')?.getFilterValue() as string) ?? ''}
            onChange={(event) => table.getColumn('name')?.setFilterValue(event.target.value)}
            className='max-w-sm'
          />
          <Button variant='outline' onClick={() => fetchInvocs()}>
            <RefreshCw className='w-4 h-4' />
          </Button>
        </div>
        <div>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='outline' className='h-8 w-8 p-0'>
                <MoreVertical />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align='start'>
              <DropdownMenuItem className='flex gap-4' onClick={() => setDialogOpen(true)}>
                <Plus className='w-4 h-4' /> Ajouter une invocation
              </DropdownMenuItem>
              <DropdownMenuItem className='flex gap-4 text-red-700 hover:!text-red-700' onClick={deleteInvoc}>
                <Trash className='w-4 h-4' /> Supprimer les invocations
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
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
                  Aucune invocation trouvée.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      {dialogOpen && (
        <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
          <DialogContent className='sm:max-w-[625px]'>
            <DialogHeader>
              <DialogTitle>Invocer un monstre</DialogTitle>
              <DialogDescription>Choissisez le joueur recevant l'invocation aléatoire</DialogDescription>
            </DialogHeader>
            <Form {...form}>
              <form onSubmit={form.handleSubmit(randomInvoc)} className='space-y-8'>
                <FormField
                  control={form.control}
                  name='playerId'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Joueur sélectionné</FormLabel>
                      <FormControl>
                        <Select onValueChange={field.onChange} value={field.value}>
                          <SelectTrigger>
                            <SelectValue placeholder='Bob' />
                          </SelectTrigger>
                          <SelectContent>
                            {players.map((p: Player) => (
                              <SelectItem key={p.id} value={p.id}>
                                {p.name}
                              </SelectItem>
                            ))}
                          </SelectContent>
                        </Select>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <Button type='submit'>Enregistrer</Button>
              </form>
            </Form>
          </DialogContent>
        </Dialog>
      )}
    </div>
  );
}
