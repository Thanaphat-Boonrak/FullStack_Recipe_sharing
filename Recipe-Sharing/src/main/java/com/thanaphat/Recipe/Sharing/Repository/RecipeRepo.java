package com.thanaphat.Recipe.Sharing.Repository;

import com.thanaphat.Recipe.Sharing.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
}
