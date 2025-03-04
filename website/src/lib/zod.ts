import { z } from 'zod';

export const newPlayerSchema = z.object({
  name: z
    .string()
    .min(2, {
      message: 'Username must be at least 2 characters.',
    })
    .max(50, {
      message: 'Username must be shorter than 50 characters.',
    }),

  level: z.number(),
});
