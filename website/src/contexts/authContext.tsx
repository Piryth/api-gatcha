import { axiosConfig } from '@/config/axiosConfig';
import { createContext, useContext, useEffect, useState } from 'react';
import { ReactNode } from 'react';

const AuthContext = createContext<{
  authUser: any;
  setAuthUser: React.Dispatch<React.SetStateAction<any>>;
  loading: boolean;
}>({
  authUser: null,
  setAuthUser: () => {},
  loading: true,
});

export const useAuthContext = () => {
  return useContext(AuthContext);
};

export const AuthContextProvider = ({ children }: { children: ReactNode }) => {
  const [authUser, setAuthUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getAuthUser = async () => {
      setLoading(true);
      try {
        const userResponse = await axiosConfig.get('/auth-api/v1/auth/me');
        const userData = userResponse.data;
        setAuthUser(userData);
      } catch (error) {
        setAuthUser(null);
      } finally {
        setLoading(false);
      }
    };

    getAuthUser();
  }, []);

  return <AuthContext.Provider value={{ authUser, setAuthUser, loading }}>{children}</AuthContext.Provider>;
};
