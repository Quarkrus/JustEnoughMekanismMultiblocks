package giselle.jei_mekanism_multiblocks.client.mixin.jei;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import mezz.jei.gui.recipes.RecipeGuiLogic;
import mezz.jei.gui.recipes.lookups.ILookupState;

@Mixin(value = RecipeGuiLogic.class, remap = false)
public interface RecipeGuiLogicAccessor
{
	@Accessor("state")
	ILookupState getState();

	@Invoker("setState")
	boolean invokeSetState(ILookupState state, boolean saveHistory);
}
