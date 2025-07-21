import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RecipeRequest } from '../model/RecipeRequest';
import { Recipe } from '../model/Recipe';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080';

  getRecipe() {
    return this.http.get<Recipe[]>(`${this.baseUrl}/api/recipe`);
  }

  CreateRecipe(Recipe: RecipeRequest) {
    return this.http.post(`${this.baseUrl}/api/recipe`, Recipe);
  }

  UpdateRecipe(Recipe: RecipeRequest, id: number) {
    return this.http.put(`${this.baseUrl}/api/recipe/${id}`, Recipe);
  }

  deleteRecipe(id: number) {
    return this.http.delete(`${this.baseUrl}/api/recipe/${id}`);
  }

}
