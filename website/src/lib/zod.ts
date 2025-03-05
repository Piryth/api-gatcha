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
