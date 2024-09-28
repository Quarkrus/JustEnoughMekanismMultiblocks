package giselle.jei_mekanism_multiblocks.client.mixin.jei;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import mezz.jei.gui.recipes.RecipeGuiLayouts;
import mezz.jei.gui.recipes.RecipeLayoutWithButtons;

@Mixin(value = RecipeGuiLayouts.class)
public interface RecipeGuiLayoutsAccessor
{
	@Accessor(value = "recipeLayoutsWithButtons")
	List<RecipeLayoutWithButtons<?>> getRecipeLayoutsWithButtons();
}
