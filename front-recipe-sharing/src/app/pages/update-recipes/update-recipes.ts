import { Component, inject, OnInit, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { RecipeService } from '../../service/recipe-service';
import { Recipe } from '../../model/Recipe';
@Component({
  selector: 'app-update-recipes',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule,
    ReactiveFormsModule,
  ],
  templateUrl: './update-recipes.html',
  styleUrl: './update-recipes.css',
})
export class UpdateRecipes {
  private dialog = inject(MatDialog);
  private fb = inject(FormBuilder);
  private recipeService = inject(RecipeService);
  loadRecipe = output();

  data = inject<{ recipe: Recipe }>(MAT_DIALOG_DATA);
  form = this.fb.group({
    title: [this.data.recipe.title, Validators.required],
    description: [this.data.recipe.description, Validators.required],
    foodType: [
      this.data.recipe.vegetarian === true ? 'veg' : 'non_veg',
      Validators.required,
    ],
    imageUrl: [this.data.recipe.image, Validators.required],
  });

  isInvalid(controlName: string): boolean {
    const control = this.form.get(controlName);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  onSubmit() {
    const recipe: Recipe = {
      title: this.form.value.title!,
      description: this.form.value.description!,
      image: this.form.value.imageUrl!,
      vegetarian: this.form.value.foodType === 'veg' ? true : false,
    };

    this.recipeService.UpdateRecipe(recipe, this.data.recipe.id!).subscribe({
      next: (res) => {
        console.log('Update Sucess', res);
        window.location.reload();
      },
    });
    this.dialog.closeAll();
  }

  onCancel(): void {
    this.dialog.closeAll();
  }
}
