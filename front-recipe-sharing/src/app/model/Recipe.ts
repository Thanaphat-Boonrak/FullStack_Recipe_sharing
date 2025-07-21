import { RecipeUser } from './RecipeUser';

export interface Recipe {
  id?: number;
  title: string;
  description: string;
  image: string;
  vegetarian: boolean;
  createdAt?: string;
  likes?: number[];
  user?: RecipeUser;
}
