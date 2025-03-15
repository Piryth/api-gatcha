import { z } from 'zod';

export const newPlayerSchema = z.object({
  name: z
    .string()
    .min(2, {
      message: 'Le nom doit contenir au moins 2 caractères.',
    })
    .max(50, {
      message: 'Le nom doit contenir au maximum 50 caractères.',
    }),

  level: z.number(),
});

export const addExpSchema = z.object({
  experience: z.string().min(1, {
    message: "L\'éxperience ne peut pas être vide.",
  }),
  id: z.string(),
});

export const invocSchema = z.object({
  playerId: z.string().min(1, 'Le joueur est requis pour invoquer un monstre.'),
});

export const newMonsterSchema = z.object({
  playerId: z.string()
});

export const loginSchema = z.object({
  password: z.string().min(6, {
    message: ' The password must be at least 6 characters long.',
  }),
  username: z
    .string()
    .min(2, {
      message: 'The username must be at least 2 characters long.',
    })
    .max(50, {
      message: 'The username must be at most 50 characters long.',
    }),
});

export const registerSchema = z
  .object({
    username: z.string().min(1, 'Username is required'),
    email: z.string().email('Invalid email'),
    password: z.string().min(6, 'Password must be at least 6 characters long'),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ['confirmPassword'],
  });
