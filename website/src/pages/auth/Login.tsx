import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { loginSchema } from '@/lib/zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { FileJson } from 'lucide-react';
import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { z } from 'zod';
import { useAuthContext } from '@/contexts/authContext';
import { axiosConfig } from '@/config/axiosConfig';
import { toast } from 'sonner';

export default function LoginPage() {
  const loginForm = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const { setAuthUser } = useAuthContext();
  const navigate = useNavigate();

  async function login(values: z.infer<typeof loginSchema>) {
    try {
      const response = await axiosConfig.post('/auth-api/v1/auth/login', values);
      const data = response.data;
      document.cookie = `Bearer=${data.token}; path=/; SameSite=Lax`;
      toast.success('Utilisateur connecté avec succès');
      setAuthUser(data.user);
      navigate('/');
    } catch (error: any) {
      if (error.response) {
        toast.error(error.response.data);
      } else {
        toast.error("Une erreur s'est produite, veuillez réessayer");
      }
    }
  }

  return (
    <div className='flex min-h-svh flex-col items-center justify-center gap-6 bg-muted p-6 md:p-10'>
      <div className='flex w-full max-w-sm flex-col gap-6'>
        <a href='#' className='flex items-center gap-2 self-center font-medium'>
          <div className='flex h-6 w-6 items-center justify-center rounded-md bg-primary text-primary-foreground'>
            <FileJson className='size-4' />
          </div>
          API Gatcha
        </a>
        <Card>
          <CardHeader className='text-center'>
            <CardTitle className='text-xl'>Welcome back</CardTitle>
            <CardDescription>Login to API gatcha with your username and password</CardDescription>
          </CardHeader>
          <CardContent className='flex flex-col items-center gap-6'>
            <Form {...loginForm}>
              <form onSubmit={loginForm.handleSubmit(login)} className='space-y-4 w-full'>
                <FormField
                  control={loginForm.control}
                  name='username'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Username</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormDescription>Enter your username</FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={loginForm.control}
                  name='password'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Password</FormLabel>
                      <FormControl>
                        <Input type='password' {...field} />
                      </FormControl>
                      <FormDescription>Enter your password</FormDescription>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <Button type='submit' className='w-full'>
                  Login
                </Button>
              </form>
            </Form>

            <div className='text-center text-sm'>
              Don&apos;t have an account?{' '}
              <Link to='/register' className='underline underline-offset-4'>
                Sign up
              </Link>
            </div>
          </CardContent>
        </Card>
        <div className='text-balance text-center text-xs text-muted-foreground [&_a]:underline [&_a]:underline-offset-4 [&_a]:hover:text-primary  '>
          By clicking continue, you agree to our <a href='#'>Terms of Service</a> and <a href='#'>Privacy Policy</a>.
        </div>
      </div>
    </div>
  );
}
