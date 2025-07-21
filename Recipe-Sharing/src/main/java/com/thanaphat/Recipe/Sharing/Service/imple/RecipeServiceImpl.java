package com.thanaphat.Recipe.Sharing.Service.imple;

import com.thanaphat.Recipe.Sharing.Repository.RecipeRepo;
import com.thanaphat.Recipe.Sharing.Service.RecipeService;
import com.thanaphat.Recipe.Sharing.model.Recipe;
import com.thanaphat.Recipe.Sharing.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements
        RecipeService {

    private final RecipeRepo recipeRepo;

    @Override
    public Recipe CreateRecipe(Recipe recipe,
                               Users user) {
        Recipe newRecipe = new Recipe();
        newRecipe.setUser(user);
        newRecipe.setTitle(recipe.getTitle());
        newRecipe.setDescription(recipe.getDescription());
        newRecipe.setImage(recipe.getImage());
        newRecipe.setCreatedAt(LocalDateTime.now());
        newRecipe.setVegetarian(recipe.isVegetarian());

        return recipeRepo.save(newRecipe);
    }

    @Override
    public Recipe FindRecipeById(Long id)
            throws Exception {
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        }
        throw  new Exception("Recipe Not Found = id " + id);
    }

    @Override
    public void deleteRecipe(Long id)
            throws Exception {
        FindRecipeById(id);
        recipeRepo.deleteById(id);
    }

    @Override
    public Recipe UpdateRecipe(Recipe recipe,
                               Long id)
            throws Exception {
        Recipe oldRecipe = FindRecipeById(id);
        if(recipe.getTitle() != null){
            oldRecipe.setTitle(recipe.getTitle());
        }
        if(recipe.getDescription() != null){
            oldRecipe.setDescription(recipe.getDescription());
        }
        if(recipe.getImage() != null){
            oldRecipe.setImage(recipe.getImage());
        }

        if(recipe.isVegetarian() != oldRecipe.isVegetarian()){
            oldRecipe.setVegetarian(recipe.isVegetarian());
        }

        return recipeRepo.save(oldRecipe);
    }

    @Override
    public List<Recipe> GetAllRecipes() {
        return recipeRepo.findAll();
    }

}
