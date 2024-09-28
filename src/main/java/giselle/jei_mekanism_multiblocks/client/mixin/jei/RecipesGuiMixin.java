package giselle.jei_mekanism_multiblocks.client.mixin.jei;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import giselle.jei_mekanism_multiblocks.client.IRecipeLayoutHolder;
import giselle.jei_mekanism_multiblocks.client.IRecipeLogicStateListener;
import giselle.jei_mekanism_multiblocks.common.JEI_MekanismMultiblocks;
import mezz.jei.api.gui.IRecipeLayoutDrawable;
import mezz.jei.gui.recipes.RecipeGuiLayouts;
import mezz.jei.gui.recipes.RecipeLayoutWithButtons;
import mezz.jei.gui.recipes.RecipesGui;

@Mixin(value = RecipesGui.class, remap = false)
public class RecipesGuiMixin implements IRecipeLayoutHolder, IRecipeLogicStateListener
{
	@Shadow
	private RecipeGuiLayouts layouts;

	@Unique
	private boolean jei_mekanism_multiblocks$onStateChangeWasCaught;

	@Shadow
	private void updateLayout()
	{
		throw new AssertionError();
	}

	@Override
	public List<IRecipeLayoutDrawable<?>> jei_mekanism_multiblocks$getRecipeLayouts()
	{
		List<IRecipeLayoutDrawable<?>> list = new ArrayList<>();

		if (!jei_mekanism_multiblocks$onStateChangeWasCaught)
		{
			try
			{
				for (RecipeLayoutWithButtons<?> recipeLayoutWithButotn : ((RecipeGuiLayoutsAccessor) layouts).getRecipeLayoutsWithButtons())
				{
					list.add(recipeLayoutWithButotn.recipeLayout());
				}

			}
			catch (Throwable e)
			{
				this.jei_mekanism_multiblocks$onStateChangeWasCaught = true;
				JEI_MekanismMultiblocks.LOGGER.error("", e);
			}

		}

		return list;
	}

	@Override
	public void jei_mekanism_multiblocks$onStateChange()
	{
		this.updateLayout();
	}

}
