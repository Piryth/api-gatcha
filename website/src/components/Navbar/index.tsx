import { Link } from 'react-router-dom';
import { ThemeChanger } from './ThemeChanger';
import { Briefcase, FileText, House, Menu, X, User } from 'lucide-react';
import { useState, useEffect, useRef } from 'react';
import { cn } from '@/lib/utils';
import { Button } from '../ui/button';
import { Separator } from '../ui/separator';

export const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const menuRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (menuRef.current && !menuRef.current.contains(event.target) && isOpen) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [isOpen]);

  return (
    <>
      <div className='navbar sticky top-0 left-0 bg-background dark:bg-background right-0 z-50 shadow-md shadow-primary'>
        <div className='hidden md:flex items-center justify-between p-4 text-primary px-8 select-none'>
          <div className='font-extrabold text-3xl'>
            <Link to='/'>API Gatcha</Link>
          </div>
          <div className='flex gap-4 items-center'>
            <div className='flex gap-2'>
              <Button asChild variant='link'>
                <Link to='/'>Accueil</Link>
              </Button>
              <Button asChild variant='link'>
                <Link to='/joueurs'>Joueur</Link>
              </Button>
              <Button asChild variant='link'>
                <Link to='/monstres'>Monstre</Link>
              </Button>
            </div>
            <Separator orientation='vertical' className='h-6' />
            <div className='flex gap-4 items-center justify-between'>
              <ThemeChanger />
            </div>
          </div>
        </div>

        <div className='flex md:hidden justify-between items-center p-4'>
          <div className='font-extrabold text-3xl'>
            <Link to='/'>API Gatcha</Link>
          </div>
          <Menu onClick={() => setIsOpen(!isOpen)} className='cursor-pointer' />
        </div>

        <div
          ref={menuRef}
          className={cn(
            'fixed top-0 right-0 w-4/5 h-screen overflow-hidden bg-white dark:bg-black text-primary transition-transform duration-300 ease-in-out z-20',
            isOpen ? 'translate-x-0' : 'translate-x-full'
          )}
        >
          <div className='flex justify-end'>
            <X onClick={() => setIsOpen(!isOpen)} className='m-4 cursor-pointer' />
          </div>

          <div className='flex flex-col gap-4 p-8 pt-2'>
            <Button asChild variant='link' className='flex gap-4 items-center justify-start' onClick={() => setIsOpen(!isOpen)}>
              <Link to='/'>
                <House className='w-4 h-4' />
                Accueil
              </Link>
            </Button>
            <Button asChild variant='link' className='flex gap-4 items-center justify-start' onClick={() => setIsOpen(!isOpen)}>
              <Link to='/joueurs'>
                <User className='w-4 h-4' />
                Joueur
              </Link>
            </Button>
            <Button asChild variant='link' className='flex gap-4 items-center justify-start' onClick={() => setIsOpen(!isOpen)}>
              <Link to='/monstres'>
                <Briefcase className='w-4 h-4' />
                Monstre
              </Link>
            </Button>
            <Separator />
            <div className='flex gap-4 justify-center items-center '>
              <ThemeChanger />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
