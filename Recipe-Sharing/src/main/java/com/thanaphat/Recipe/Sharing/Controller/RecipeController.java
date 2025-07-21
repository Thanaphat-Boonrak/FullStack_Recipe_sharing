package com.thanaphat.Recipe.Sharing.Controller;


import com.thanaphat.Recipe.Sharing.Service.RecipeService;
import com.thanaphat.Recipe.Sharing.Service.UserService;
import com.thanaphat.Recipe.Sharing.model.Recipe;
import com.thanaphat.Recipe.Sharing.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    @PostMapping()
    public Recipe RecipeCreate(@RequestBody Recipe recipe, Authentication authentication)
            throws Exception {
        Users user = userService.findUserByEmail(authentication.getName());
        return recipeService.CreateRecipe(recipe, user);
    }


    @GetMapping
    public List<Recipe> GetAllRecipe()
            throws Exception {
        return recipeService.GetAllRecipes();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable Long id) throws Exception {
        recipeService.deleteRecipe(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Recipe deleted");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public Recipe UpdateRecipe(@RequestBody Recipe recipe, @PathVariable Long id)
            throws Exception {
        return recipeService.UpdateRecipe(recipe, id);
    }


}
