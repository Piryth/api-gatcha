export const ENUM_ELEMENT = {
  FIRE: 'Feu',
  WATER: 'Eau',
  EARTH: 'Terre',
  WIND: 'Vent',
  LIGHT: 'Lumière',
  DARK: 'Ténèbres',
};

export type ElementType = (typeof ENUM_ELEMENT)[keyof typeof ENUM_ELEMENT];
