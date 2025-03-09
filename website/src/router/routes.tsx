import { Outlet, Route, Routes } from 'react-router-dom';
import { Navbar } from '../components/Navbar';
import { Footer } from '@/components/Footer';
import { Home } from '@/pages/Home';
import { Monsters } from '@/pages/Monsters';
import { Players } from '@/pages/Players';
import { Register } from '@/pages/auth/Register';
import Login from '@/pages/auth/Login';
import { ProtectedRoute } from './authRequired';

const RootLayout = () => (
  <div className='flex flex-col min-h-screen'>
    <Navbar />
    <main className='flex-grow'>
      <Outlet />
    </main>
    <Footer />
  </div>
);

export const Router = () => {
  return (
    <Routes>
      <Route
        path='/login'
        element={
          <ProtectedRoute authRequired={false}>
            <Login />
          </ProtectedRoute>
        }
      />
      <Route
        path='/register'
        element={
          <ProtectedRoute authRequired={false}>
            <Register />
          </ProtectedRoute>
        }
      />
      <Route
        path='/'
        element={
          <ProtectedRoute authRequired={true}>
            <RootLayout />
          </ProtectedRoute>
        }
        children={[
          <Route path='/' element={<Home />} key={'/'} />,
          <Route path='/monsters' element={<Monsters />} key={'/monsters'} />,
          <Route path='/players' element={<Players />} key={'/players'} />,
        ]}
      />
    </Routes>
  );
};
