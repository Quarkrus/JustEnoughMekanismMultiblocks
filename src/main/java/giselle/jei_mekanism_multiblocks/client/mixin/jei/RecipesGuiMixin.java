package giselle.jei_mekanism_multiblocks.client.mixin.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import giselle.jei_mekanism_multiblocks.client.IRecipeLayoutHolder;
import giselle.jei_mekanism_multiblocks.client.IRecipeLogicStateListener;
import giselle.jei_mekanism_multiblocks.common.JEI_MekanismMultiblocks;
import mezz.jei.api.gui.IRecipeLayoutDrawable;
import mezz.jei.gui.recipes.IRecipeGuiLogic;
import mezz.jei.gui.recipes.RecipeGuiLayouts;
import mezz.jei.gui.recipes.RecipeLayoutWithButtons;
import mezz.jei.gui.recipes.RecipesGui;
import mezz.jei.gui.recipes.lookups.ILookupState;

@Mixin(value = RecipesGui.class, remap = false)
public abstract class RecipesGuiMixin implements IRecipeLayoutHolder, IRecipeLogicStateListener
{
	@Shadow
	private IRecipeGuiLogic logic;

	@Shadow
	private RecipeGuiLayouts layouts;

	@Unique
	private boolean jei_mekanism_multiblocks$getRecipeLayoutsWasCaught;
	@Unique
	private boolean jei_mekanism_multiblocks$onStateChangeWasCaught;

	@Override
	public List<IRecipeLayoutDrawable<?>> jei_mekanism_multiblocks$getRecipeLayouts()
	{
		if (!this.jei_mekanism_multiblocks$getRecipeLayoutsWasCaught)
		{
			try
			{
				List<IRecipeLayoutDrawable<?>> list = new ArrayList<>();

				if (this.layouts instanceof RecipeGuiLayoutsAccessor accessor)
				{
					for (RecipeLayoutWithButtons<?> recipeLayoutWithButtons : accessor.getRecipeLayoutsWithButtons())
					{
						list.add(recipeLayoutWithButtons.recipeLayout());
					}

				}

				return list;
			}
			catch (Throwable e)
			{
				this.jei_mekanism_multiblocks$getRecipeLayoutsWasCaught = true;
				JEI_MekanismMultiblocks.LOGGER.error("", e);
			}

		}

		return Collections.emptyList();
	}

	@Override
	public void jei_mekanism_multiblocks$onStateChange()
	{
		if (!jei_mekanism_multiblocks$onStateChangeWasCaught)
		{
			try
			{
				if (this.logic instanceof RecipeGuiLogicAccessor accessor)
				{
					ILookupState state = accessor.getState();
					accessor.invokeSetState(state, false);
				}

			}
			catch (Throwable e)
			{
				this.jei_mekanism_multiblocks$onStateChangeWasCaught = true;
				JEI_MekanismMultiblocks.LOGGER.error("", e);
			}

		}

	}

}
