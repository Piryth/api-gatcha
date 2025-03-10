import { Link, useNavigate } from 'react-router-dom';
import { ThemeChanger } from './ThemeChanger';
import { Briefcase, FileText, House, Menu, X, User, LogOut, Github } from 'lucide-react';
import { useState, useEffect, useRef } from 'react';
import { cn } from '@/lib/utils';
import { Button } from '../ui/button';
import { Separator } from '../ui/separator';
import { useAuthContext } from '@/contexts/authContext';
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuSub,
  DropdownMenuSubTrigger,
  DropdownMenuPortal,
  DropdownMenuSubContent,
} from '../ui/dropdown-menu';
import { DropdownMenuShortcut } from '../ui/dropdown-menu';
import { toast } from 'sonner';

export const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const menuRef = useRef(null);
  const navigate = useNavigate();
  const { authUser, setAuthUser } = useAuthContext();

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (menuRef.current && !menuRef.current.contains(event.target) && isOpen) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [isOpen]);

  async function logout() {
    document.cookie = 'Bearer=; expires=Thu, 01 Jan 1970 00:00:00 UTC;';
    setAuthUser(null);
    navigate('/login');
    toast.success('Déconnexion réussie');
  }

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
                <Link to='/players'>Joueurs</Link>
              </Button>
              <Button asChild variant='link'>
                <Link to='/monsters'>Monstres</Link>
              </Button>
            </div>
            <Separator orientation='vertical' className='h-6' />
            <div className='flex gap-4 items-center justify-between'>
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant='outline' size='sm'>
                    <User />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className='w-56 '>
                  <DropdownMenuLabel>Username : {authUser?.username}</DropdownMenuLabel>

                  {/* <DropdownMenuSeparator />
                  <DropdownMenuItem>
                    <Link target='_blank' to={'https://github.com/Piryth/api-gatcha'}>
                      Source code
                    </Link>
                    <DropdownMenuShortcut>
                      <Github className='w-4 h-4' />
                    </DropdownMenuShortcut>
                  </DropdownMenuItem> */}
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => logout()}>
                    Se déconnecter
                    <DropdownMenuShortcut>
                      <LogOut className='w-4 h-4' />
                    </DropdownMenuShortcut>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
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
              <Link to='/players'>
                <User className='w-4 h-4' />
                Joueurs
              </Link>
            </Button>
            <Button asChild variant='link' className='flex gap-4 items-center justify-start' onClick={() => setIsOpen(!isOpen)}>
              <Link to='/monsters'>
                <Briefcase className='w-4 h-4' />
                Monstres
              </Link>
            </Button>
            <Separator />
            <div className='flex gap-4 justify-center items-center '>
              <ThemeChanger />
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant='outline' size='sm'>
                    <User />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className='w-56 '>
                  <DropdownMenuLabel>{authUser?.username}</DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => logout()}>
                    Se déconnecter
                    <DropdownMenuShortcut>
                      <LogOut className='w-4 h-4' />
                    </DropdownMenuShortcut>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
