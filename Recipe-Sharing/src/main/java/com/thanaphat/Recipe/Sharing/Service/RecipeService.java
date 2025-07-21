package com.thanaphat.Recipe.Sharing.Service;

import com.thanaphat.Recipe.Sharing.model.Recipe;
import com.thanaphat.Recipe.Sharing.model.Users;

import java.util.List;

public interface RecipeService {

    public Recipe CreateRecipe(Recipe recipe,
                               Users user);

    public Recipe FindRecipeById(Long id) throws Exception;

    public void deleteRecipe(Long id) throws Exception;

    public Recipe UpdateRecipe(Recipe recipe,Long id) throws Exception;

    public List<Recipe> GetAllRecipes();
}
