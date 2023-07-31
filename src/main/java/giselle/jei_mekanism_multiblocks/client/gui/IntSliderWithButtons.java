package giselle.jei_mekanism_multiblocks.client.gui;

import com.ibm.icu.text.DecimalFormat;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class IntSliderWithButtons extends ContainerWidget
{
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("+#;-#");

	private String translationKey;

	private final IntSliderWidget slider;
	private final ButtonWidget minusButton;
	private final ButtonWidget plusButton;

	public IntSliderWithButtons(int pX, int pY, int pWidth, int pHeight, String translationKey, int value, int min, int max)
	{
		this(pX, pY, pWidth, pHeight, translationKey, new IntSliderWidget(0, 0, 0, 0, StringTextComponent.EMPTY, value, min, max));
	}

	public IntSliderWithButtons(int pX, int pY, int pWidth, int pHeight, String translationKey, IntSliderWidget slider)
	{
		super(pX, pY, pWidth, pHeight);
		this.translationKey = translationKey;

		this.addChild(this.slider = slider);
		this.slider.addIntValueChangeHanlder(v ->
		{
			this.updateMessage();
		});
		this.addChild(this.minusButton = this.createAdjustButton(new StringTextComponent("-"), -1));
		this.addChild(this.plusButton = this.createAdjustButton(new StringTextComponent("+"), +1));

		this.updateMessage();
		this.onHeightChanged();
	}

	protected void updateMessage()
	{
		this.getSlider().setMessage(new TranslationTextComponent(this.translationKey, String.valueOf(this.getSlider().getIntValue())));
	}

	private ButtonWidget createAdjustButton(ITextComponent component, int direction)
	{
		int shiftDelta = 5;
		int normalDelta = 1;

		ButtonWidget button = new ButtonWidget(0, 0, 0, 0, component);
		button.setTooltip(//
				new TranslationTextComponent("text.jei_mekanism_multiblocks.click.normal", DECIMAL_FORMAT.format(direction * normalDelta)), //
				new TranslationTextComponent("text.jei_mekanism_multiblocks.click.shift", DECIMAL_FORMAT.format(direction * shiftDelta)));
		button.addPressHandler(b ->
		{
			int intValue = this.slider.getIntValue();
			int delta = Screen.hasShiftDown() ? shiftDelta : normalDelta;
			this.slider.setIntValue(intValue + delta * direction);
		});
		return button;
	}

	@Override
	protected void onWidthChanged()
	{
		super.onWidthChanged();

		this.updateChildrenBounds();
	}

	@Override
	protected void onHeightChanged()
	{
		super.onHeightChanged();

		int height = this.getHeight();

		IntSliderWidget slider = this.getSlider();
		slider.setHeight(height);

		ButtonWidget minusButton = this.getMinusButton();
		minusButton.setWidth(height);
		minusButton.setHeight(height);

		ButtonWidget plusButton = this.getPlusButton();
		plusButton.setWidth(height);
		plusButton.setHeight(height);

		this.updateChildrenBounds();
	}

	protected void updateChildrenBounds()
	{
		ButtonWidget plusButton = this.getPlusButton();
		plusButton.x = this.getWidth() - plusButton.getWidth();
		plusButton.y = 0;

		ButtonWidget minusButton = this.getMinusButton();
		minusButton.x = plusButton.x - minusButton.getWidth();
		minusButton.y = plusButton.y;

		IntSliderWidget slider = this.getSlider();
		slider.x = 0;
		slider.y = minusButton.y;
		slider.setWidth(minusButton.x - slider.x);
	}

	public void setTranslationKey(String translationKey)
	{
		this.translationKey = translationKey;
		this.updateMessage();
	}

	public String getTranslationKey()
	{
		return this.translationKey;
	}

	public ButtonWidget getMinusButton()
	{
		return this.minusButton;
	}

	public ButtonWidget getPlusButton()
	{
		return this.plusButton;
	}

	public IntSliderWidget getSlider()
	{
		return this.slider;
	}

}
