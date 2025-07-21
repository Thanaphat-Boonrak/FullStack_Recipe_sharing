import { Component, inject, OnInit, signal } from '@angular/core';
import { RecipeCard } from '../recipe-card/recipe-card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { MatDialog } from '@angular/material/dialog';
import { CreateRecipeForm } from '../create-recipe-form/create-recipe-form';
import { AuthService } from '../../service/auth-service';
import { RecipeService } from '../../service/recipe-service';
import { Recipe } from '../../model/Recipe';

@Component({
  selector: 'app-home-pages',
  imports: [RecipeCard, MatIconModule, MatButtonModule],
  templateUrl: './home-pages.html',
  styleUrl: './home-pages.css',
})
export class HomePages implements OnInit {
  private dilog = inject(MatDialog);
  private authService = inject(AuthService);
  private recipeService = inject(RecipeService);
  recipe = signal<Recipe[]>([]);

  ngOnInit() {
    this.loadRecipe();
  }

  loadRecipe() {
    this.recipeService.getRecipe().subscribe({
      next: (res: Recipe[]) => {
        this.recipe.set(res);
      },
    });
  }

  deleteRecipe(id: number) {
    this.recipeService.deleteRecipe(id).subscribe({
      next: () => {
        console.log('delete Sucess');
        const update = this.recipe().filter((r) => r.id !== id);
        this.recipe.set(update);
      },
    });
  }

  onClick() {
    const dialogRef = this.dilog.open(CreateRecipeForm);

    dialogRef.componentInstance.loadRecipe.subscribe(() => {
      this.loadRecipe();
    });
  }
}
