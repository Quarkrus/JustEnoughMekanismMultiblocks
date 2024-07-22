package giselle.jei_mekanism_multiblocks.client.jei.category;

import java.util.function.Consumer;

import giselle.jei_mekanism_multiblocks.client.gui.CheckBoxWidget;
import giselle.jei_mekanism_multiblocks.client.gui.IntSliderWithButtons;
import giselle.jei_mekanism_multiblocks.client.jei.MultiblockCategory;
import giselle.jei_mekanism_multiblocks.client.jei.MultiblockWidget;
import giselle.jei_mekanism_multiblocks.client.jei.ResultWidget;
import giselle.jei_mekanism_multiblocks.common.util.VolumeTextHelper;
import mekanism.api.math.MathUtils;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.util.text.EnergyDisplay;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class SPSCategory extends MultiblockCategory<SPSCategory.SPSWidget>
{
	public SPSCategory(IGuiHelper helper)
	{
		super(helper, Mekanism.rl("sps"), SPSWidget.class, MekanismLang.SPS.translate(), MekanismBlocks.SPS_PORT.getItemStack());
	}

	@Override
	protected void getRecipeCatalystItemStacks(Consumer<ItemStack> consumer)
	{
		super.getRecipeCatalystItemStacks(consumer);
		consumer.accept(MekanismBlocks.SPS_CASING.getItemStack());
		consumer.accept(MekanismBlocks.SPS_PORT.getItemStack());
		consumer.accept(MekanismBlocks.SUPERCHARGED_COIL.getItemStack());
		consumer.accept(MekanismBlocks.STRUCTURAL_GLASS.getItemStack());
	}

	public static class SPSWidget extends MultiblockWidget
	{
		protected CheckBoxWidget useStructuralGlassCheckBox;
		protected IntSliderWithButtons portsWidget;

		public SPSWidget()
		{

		}

		@Override
		protected void collectOtherConfigs(Consumer<AbstractWidget> consumer)
		{
			super.collectOtherConfigs(consumer);

			consumer.accept(this.useStructuralGlassCheckBox = new CheckBoxWidget(0, 0, 0, 0, Component.translatable("text.jei_mekanism_multiblocks.specs.use_things", MekanismBlocks.STRUCTURAL_GLASS.getItemStack().getHoverName()), true));
			this.useStructuralGlassCheckBox.addSelectedChangedHandler(this::onUseStructuralGlassChanged);
			consumer.accept(this.portsWidget = new IntSliderWithButtons(0, 0, 0, 0, "text.jei_mekanism_multiblocks.specs.ports", 0, 3, this.getSideBlocks()));
			this.portsWidget.getSlider().addValueChangeHanlder(this::onPortsChanged);
		}

		@Override
		protected void collectCost(ICostConsumer consumer)
		{
			super.collectCost(consumer);

			int corners = this.getCornerBlocks();
			int sides = this.getSideBlocks();
			int ports = this.getPortCount();
			sides -= ports;

			int casing = 0;
			int structuralGlasses = 0;

			if (this.isUseStruturalGlass())
			{
				casing = corners;
				structuralGlasses = sides;
			}
			else
			{
				casing = corners + sides;
				structuralGlasses = 0;
			}

			consumer.accept(new ItemStack(MekanismBlocks.SPS_CASING, casing));
			consumer.accept(new ItemStack(MekanismBlocks.SPS_PORT, ports));
			consumer.accept(new ItemStack(MekanismBlocks.SUPERCHARGED_COIL));
			consumer.accept(new ItemStack(MekanismBlocks.STRUCTURAL_GLASS, structuralGlasses));
		}

		@Override
		protected void collectResult(Consumer<AbstractWidget> consumer)
		{
			super.collectResult(consumer);

			long energyPerAntimatter = MathUtils.multiplyClamped(MekanismConfig.general.spsEnergyPerInput.get(), MekanismConfig.general.spsInputPerAntimatter.get());
			consumer.accept(new ResultWidget(Component.translatable("text.jei_mekanism_multiblocks.result.energy_per_antimatter"), Component.translatable("%s/%s", EnergyDisplay.of(energyPerAntimatter).getTextComponent(), "mB")));
			consumer.accept(new ResultWidget(Component.translatable("text.jei_mekanism_multiblocks.result.input_tank"), VolumeTextHelper.formatMB(MekanismConfig.general.spsInputPerAntimatter.get() * 2L)));
			consumer.accept(new ResultWidget(Component.translatable("text.jei_mekanism_multiblocks.result.output_tank"), VolumeTextHelper.formatMB(MekanismConfig.general.spsOutputTankCapacity.get())));
		}

		protected void onPortsChanged(int ports)
		{
			this.markNeedUpdate();
		}

		protected void onUseStructuralGlassChanged(boolean useStructuralGlass)
		{
			this.markNeedUpdate();
		}

		@Override
		public int getCornerBlocks()
		{
			return 60;
		}

		@Override
		public int getSideBlocks()
		{
			return 126;
		}

		public int getPortCount()
		{
			return this.portsWidget.getSlider().getValue();
		}

		public void setPortCount(int portCount)
		{
			this.portsWidget.getSlider().setValue(portCount);
		}

		public boolean isUseStruturalGlass()
		{
			return this.useStructuralGlassCheckBox.isSelected();
		}

		public void setUseStructuralGlass(boolean useStructuralGlass)
		{
			this.useStructuralGlassCheckBox.setSelected(useStructuralGlass);
		}

		@Override
		public int getDimensionWidthMin()
		{
			return 7;
		}

		@Override
		public int getDimensionWidthMax()
		{
			return 7;
		}

		@Override
		public int getDimensionLengthMin()
		{
			return 7;
		}

		@Override
		public int getDimensionLengthMax()
		{
			return 7;
		}

		@Override
		public int getDimensionHeightMin()
		{
			return 7;
		}

		@Override
		public int getDimensionHeightMax()
		{
			return 7;
		}

	}

}
