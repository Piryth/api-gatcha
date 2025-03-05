export const ENUM_ELEMENT = {
  FIRE: 'Feu',
  WATER: 'Eau',
  EARTH: 'Terre',
  WIND: 'Vent',
  LIGHT: 'Lumière',
  DARK: 'Ténèbres',
  UNKNOWN: 'Inconnu',
};

export type ElementType = (typeof ENUM_ELEMENT)[keyof typeof ENUM_ELEMENT];
