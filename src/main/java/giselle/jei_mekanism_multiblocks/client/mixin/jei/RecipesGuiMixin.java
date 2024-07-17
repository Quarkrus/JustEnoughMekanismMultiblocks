package giselle.jei_mekanism_multiblocks.client.mixin.jei;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import giselle.jei_mekanism_multiblocks.client.IRecipeLayoutHolder;
import giselle.jei_mekanism_multiblocks.client.IRecipeLogicStateListener;
import mezz.jei.api.gui.IRecipeLayoutDrawable;
import mezz.jei.gui.recipes.RecipeGuiLayouts;
import mezz.jei.gui.recipes.RecipeLayoutWithButtons;
import mezz.jei.gui.recipes.RecipesGui;

@Mixin(value = RecipesGui.class, remap = false)
public abstract class RecipesGuiMixin implements IRecipeLayoutHolder, IRecipeLogicStateListener
{
	@Shadow
	private RecipeGuiLayouts layouts;

	@Override
	public List<IRecipeLayoutDrawable<?>> jei_mekanism_multiblocks$getRecipeLayouts()
	{
		List<IRecipeLayoutDrawable<?>> list = new ArrayList<>();

		for (RecipeLayoutWithButtons<?> recipeLayoutWithButtons : ((RecipeGuiLayoutsAccessor) this.layouts).getRecipeLayoutsWithButtons())
		{
			list.add(recipeLayoutWithButtons.getRecipeLayout());
		}

		return list;
	}

	@Shadow
	private void updateLayout()
	{

	}

	@Override
	public void jei_mekanism_multiblocks$onStateChange()
	{
		this.updateLayout();
	}

}
