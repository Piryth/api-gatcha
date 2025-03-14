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
import { addExpSchema, newMonsterSchema, newPlayerSchema } from '@/lib/zod';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { ElementType, ENUM_ELEMENT } from '@/types/ElementType';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { axiosConfig } from '@/config/axiosConfig';

export type Monster = {
  id: string;
  name: string;
  element: ElementType;
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
  level: number;
};

export function Monsters() {
  const [monsters, setMonsters] = React.useState<Monster[]>([]);
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = React.useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = React.useState({});

  const [open, setOpen] = React.useState(false);

  const form = useForm<z.infer<typeof newMonsterSchema>>({
    resolver: zodResolver(newMonsterSchema),
    defaultValues: {
      name: '',
      hp: '0',
      atk: '0',
      def: '0',
      vit: '0',
      element: '',
    },
  });

  React.useEffect(() => {
    fetchMonsters();
  }, []);

  async function fetchMonsters() {
    try {
      const response = await axiosConfig.get('/monsters/list');
      const data = await response.data;
      setMonsters(data);
      toast.success('Monstres récupérés avec succès');
    } catch (error) {
      toast.error('Erreur lors de la récupération des monstres :', error);
    }
  }

  async function deleteMonster(monsterId: String) {
    try {
      await axiosConfig.delete(`/monsters/${monsterId}`);
      const monsterName = monsters.find((m) => m.id == monsterId)?.name;
      toast.success(`Monstre ${monsterName} supprimé avec succès`);
      setMonsters(monsters.filter((m) => m.id != monsterId));
    } catch (error) {
      toast.error("Erreur lors de la suppression d'un monstre :", error);
    }
  }

  async function levelUp(monsterId: String) {
    try {
      await axiosConfig.put(`/monsters/${monsterId}/levelUp`);
      await fetchMonsters();
      const monsterName = monsters.find((m) => m.id == monsterId)?.name;
      toast.success(`Monstre ${monsterName} amélioré avec succès`);
    } catch (error) {
      toast.error("Erreur lors de la suppression d'un monstre :", error);
    }
  }

  async function createMonster(values: z.infer<typeof newMonsterSchema>) {
    try {
      await axiosConfig.post('/monsters/new', values);
      await fetchMonsters();
      setOpen(false);
      form.reset();
      toast.success(`Monstre ${values.name} créé avec succès`);
    } catch (error) {
      toast.error('Erreur lors de la création du monstre :', error);
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
      cell: ({ row }) => {
        const elementKey = row.getValue('element') as keyof typeof ENUM_ELEMENT;
        return <div>{ENUM_ELEMENT[elementKey] || elementKey}</div>;
      },
    },
    {
      accessorKey: 'hp',
      header: 'Points de vie',
      cell: ({ row }) => <div>{row.getValue('hp')}</div>,
    },
    {
      accessorKey: 'atk',
      header: 'Attaque',
      cell: ({ row }) => <div>{row.getValue('atk')}</div>,
    },
    {
      accessorKey: 'def',
      header: 'Défense',
      cell: ({ row }) => <div>{row.getValue('def')}</div>,
    },
    {
      accessorKey: 'vit',
      header: 'Vitesse',
      cell: ({ row }) => <div>{row.getValue('vit')}</div>,
    },
    {
      accessorKey: 'level',
      header: 'Niveau',
      cell: ({ row }) => <div>{row.getValue('level')}</div>,
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
              <DropdownMenuItem className='flex gap-4' onClick={() => levelUp(monster.id)}>
                <ArrowUpCircleIcon className='w-4 h-4' /> Améliorer le monstre
              </DropdownMenuItem>
              <DropdownMenuItem className='flex gap-4 text-red-700 hover:!text-red-700' onClick={() => deleteMonster(monster.id)}>
                <Trash className='w-4 h-4' /> Supprimer le monstre
              </DropdownMenuItem>
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
            <Form {...form}>
              <form onSubmit={form.handleSubmit(createMonster)} className='space-y-8'>
                <FormField
                  control={form.control}
                  name='name'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nom</FormLabel>
                      <FormControl>
                        <Input placeholder='Bigfoot' {...field} />
                      </FormControl>
                      <FormDescription>Le nom visible de votre monstre</FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <div className='grid grid-cols-2 gap-8'>
                  <FormField
                    control={form.control}
                    name='hp'
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Points de vie</FormLabel>
                        <FormControl>
                          <Input type='number' placeholder='100' {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name='atk'
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Attaque</FormLabel>
                        <FormControl>
                          <Input type='number' placeholder='100' {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                <div className='grid grid-cols-2 gap-8'>
                  <FormField
                    control={form.control}
                    name='def'
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Défense</FormLabel>
                        <FormControl>
                          <Input type='number' placeholder='100' {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name='vit'
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Vitesse</FormLabel>
                        <FormControl>
                          <Input type='number' placeholder='100' {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                <FormField
                  control={form.control}
                  name='element'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Element</FormLabel>
                      <FormControl>
                        <Select onValueChange={field.onChange} value={field.value}>
                          <SelectTrigger>
                            <SelectValue placeholder='Element' />
                          </SelectTrigger>
                          <SelectContent>
                            {Object.entries(ENUM_ELEMENT).map(([key, value]) => (
                              <SelectItem key={key} value={key}>
                                {value}
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
