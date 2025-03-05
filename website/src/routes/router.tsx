import { ErrorPage } from '../pages/ErrorPage';
import { createBrowserRouter, Outlet } from 'react-router-dom';
import { Navbar } from '../components/Navbar';
import { Footer } from '@/components/Footer';
import { Home } from '@/pages/Home';
import { Monsters } from '@/pages/Monsters';
import { Players } from '@/pages/Players';

const RootLayout = () => (
  <div className='flex flex-col min-h-screen'>
    <Navbar />
    <main className='flex-grow'>
      <Outlet />
    </main>
    <Footer />
  </div>
);

export const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    errorElement: (
      <>
        <Navbar />
        <ErrorPage />
        <Footer />
      </>
    ),
    children: [
      { path: '/', element: <Home /> },
      { path: '/joueurs', element: <Players /> },
      { path: '/monstres', element: <Monsters /> },
    ],
  },
]);
