import { Component, inject, output } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatRadioModule } from '@angular/material/radio';
import { RecipeService } from '../../service/recipe-service';
import { Recipe } from '../../model/Recipe';

@Component({
  selector: 'app-create-recipe-form',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRadioModule,
  ],
  templateUrl: './create-recipe-form.html',
  styleUrl: './create-recipe-form.css',
})
export class CreateRecipeForm {
  private fb = inject(FormBuilder);
  private dialog = inject(MatDialog);
  private recipeService = inject(RecipeService);
  loadRecipe = output();
  form = this.fb.group({
    title: ['', Validators.required],
    description: ['', Validators.required],
    foodType: ['', Validators.required],
    imageUrl: ['', Validators.required],
  });

  isInvalid(controlName: string): boolean {
    const control = this.form.get(controlName);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  onCancel() {
    this.dialog.closeAll();
  }

  onSubmit() {
    const recipe: Recipe = {
      title: this.form.value.title!,
      description: this.form.value.description!,
      image: this.form.value.imageUrl!,
      vegetarian: this.form.value.foodType === 'veg' ? true : false,
    };

    this.recipeService.CreateRecipe(recipe).subscribe({
      next: (res) => {
        console.log('Create Sucess', res);
        this.loadRecipe.emit();
      },
    });
    this.dialog.closeAll();
  }
}
