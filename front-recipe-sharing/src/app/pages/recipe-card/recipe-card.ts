import {
  Component,
  inject,
  input,
  OnInit,
  output,
  signal,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { UpdateRecipes } from '../update-recipes/update-recipes';
import { Recipe } from '../../model/Recipe';
import { RecipeService } from '../../service/recipe-service';

@Component({
  selector: 'app-recipe-card',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './recipe-card.html',
  styleUrl: './recipe-card.css',
})
export class RecipeCard {
  private dialog = inject(MatDialog);
  recipe = input<Recipe>();
  deleteRecipie = output<number>();

  handleOpenEdit() {
    this.dialog.open(UpdateRecipes, {
      data: { recipe: this.recipe() },
    });
  }

  handleDelete() {
    this.deleteRecipie.emit(this.recipe()?.id!);
  }
}
