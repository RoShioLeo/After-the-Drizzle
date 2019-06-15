package roito.cultivage.environment;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.cultivage.Cultivage;

public enum Rainfall
{
	RARE,
	SCARCE,
	MODERATE,
	ADEQUATE,
	ABUNDANT;

	public int getId()
	{
		return this.ordinal() + 1;
	}

	public String getName()
	{
		return this.toString().toLowerCase();
	}

	@SideOnly(Side.CLIENT)
	public String getTranslation()
	{
		return I18n.format(Cultivage.MODID + ".environment.rainfall" + getName());
	}
}
