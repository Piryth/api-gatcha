import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './styles/index.css';
import { ThemeProvider } from './providers/theme-provider';
import { Toaster } from '@/components/ui/sonner';
import { Router } from './router/routes';
import { BrowserRouter } from 'react-router-dom';
import { AuthContextProvider } from './contexts/authContext.js';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <AuthContextProvider>
        <ThemeProvider defaultTheme='dark' storageKey='vite-ui-theme'>
          <Toaster />
          <Router />
        </ThemeProvider>
      </AuthContextProvider>
    </BrowserRouter>
  </StrictMode>
);
