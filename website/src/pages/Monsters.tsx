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
import { ArrowUpCircleIcon, Copy, MoreHorizontal, Plus, RefreshCw, Trash, X } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { toast } from 'sonner';
import { z } from 'zod';
import { addExpSchema, newPlayerSchema } from '@/lib/zod';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

export type Monster = {
  id: string;
  name: string;
  element: string;
  hp: number;
  atk: number;
  def: number;
  vit: number;
  skills: {
    damage: number;
    cooldown: number;
    lvlMax: number;
    ratio: {
      stat: string;
      percent: number;
    };
  };
};

export function Monsters() {
  const [monsters, setMonsters] = React.useState<Monster[]>([]);
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = React.useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = React.useState({});

  const [open, setOpen] = React.useState(false);

  React.useEffect(() => {
    fetchMonsters();
  }, []);

  async function fetchMonsters() {
    try {
      const response = await fetch('http://localhost:8080/monsters');
      const data = await response.json();
      setMonsters(data);
      toast.success('Monstres récupérés avec succès');
    } catch (error) {
      toast.error('Erreur lors de la récupération des monstres :', error);
    }
  }

  const columns: ColumnDef<Monster>[] = [
    {
      accessorKey: 'name',
      header: 'Nom',
      cell: ({ row }) => <div>{row.getValue('name')}</div>,
    },
    {
      accessorKey: 'element',
      header: 'Element',
      cell: ({ row }) => <div>{row.getValue('element')}</div>,
    },
    {
      accessorKey: 'hp',
      header: 'HP',
      cell: ({ row }) => <div>{row.getValue('hp')}</div>,
    },
    {
      accessorKey: 'atk',
      header: 'ATK',
      cell: ({ row }) => <div>{row.getValue('atk')}</div>,
    },
    {
      accessorKey: 'def',
      header: 'DEF',
      cell: ({ row }) => <div>{row.getValue('def')}</div>,
    },
    {
      accessorKey: 'vit',
      header: 'VIT',
      cell: ({ row }) => <div>{row.getValue('vit')}</div>,
    },
    {
      id: 'actions',
      enableHiding: false,
      header: 'Actions',
      cell: ({ row }) => {
        const monster = row.original;

        return (
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='ghost' className='h-8 w-8 p-0'>
                <MoreHorizontal />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align='start'>
              <DropdownMenuItem className='flex gap-4' onClick={() => navigator.clipboard.writeText(monster.id)}>
                <Copy className='w-4 h-4' /> Copier l'ID du monstre
              </DropdownMenuItem>
              {/* <DropdownMenuItem className='flex gap-4 text-red-700 hover:!text-red-700' onClick={() => deleteMonster(monster.id)}>
                <Trash className='w-4 h-4' /> Supprimer le monstre
              </DropdownMenuItem> */}
            </DropdownMenuContent>
          </DropdownMenu>
        );
      },
    },
  ];

  const table = useReactTable({
    data: monsters,
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
      <h1 className='text-4xl -'>Listes des monstres</h1>
      <div className='flex items-center justify-between py-4'>
        <div className='flex gap-4'>
          <Input
            placeholder='Rechercher un monstre...'
            value={(table.getColumn('name')?.getFilterValue() as string) ?? ''}
            onChange={(event) => table.getColumn('name')?.setFilterValue(event.target.value)}
            className='max-w-sm'
          />
          <div>
            <Button variant='outline' onClick={() => fetchMonsters()}>
              <RefreshCw className='w-4 h-4' />
            </Button>
          </div>
        </div>
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <Button variant='outline'>
              <Plus className='w-4 h-4' />
              <span>Ajouter un monstre</span>
            </Button>
          </DialogTrigger>
          <DialogContent className='sm:max-w-[625px]'>
            <DialogHeader>
              <DialogTitle>Création d'un nouveau monstre</DialogTitle>
              <DialogDescription>Vous pouvez créer un monstre dans cette interface.</DialogDescription>
            </DialogHeader>
            {/* <Form {...form}>
              <form onSubmit={form.handleSubmit(createNewPlayer)} className='space-y-8'>
                <FormField
                  control={form.control}
                  name='name'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nom</FormLabel>
                      <FormControl>
                        <Input placeholder='monstre 1' {...field} />
                      </FormControl>
                      <FormDescription>Le nom visible de votre monstre</FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <Button type='submit'>Enregistrer</Button>
              </form>
            </Form> */}
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
                  Aucun monstre trouvé.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
