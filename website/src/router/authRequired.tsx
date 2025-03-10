import { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthContext } from '../contexts/authContext';

export const ProtectedRoute = ({ authRequired, children }: { authRequired: boolean; children: ReactNode }) => {
  const { authUser, loading } = useAuthContext();

  if (authRequired && !authUser && !loading) {
    return <Navigate to='/login' />;
  }

  if (!authRequired && authUser && !loading) {
    return <Navigate to='/' />;
  }

  return children;
};
