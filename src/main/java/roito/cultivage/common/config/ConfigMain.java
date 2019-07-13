package roito.cultivage.common.config;

import net.minecraftforge.common.config.Config;
import roito.cultivage.Cultivage;

@Config(modid = Cultivage.MODID)
@Config.LangKey("cultivage.config")
public final class ConfigMain
{
	@Config.Name("General")
	@Config.LangKey("cultivage.config.general")
	public static final General general = new General();

	public static final class General
	{
		@Config.Comment("The ticks of drying per item outdoors. (1 second = 20 ticks)")
		@Config.LangKey("cultivage.config.flat_basket.outdoor_drying_basic_time")
		@Config.Name("DryingBasicTime")
		@Config.RangeInt(min = 250, max = 12000)
		public int dryingOutdoorsBasicTime = 800;

		@Config.Comment("The ticks of drying per item indoors. (1 second = 20 ticks)")
		@Config.LangKey("cultivage.config.flat_basket.indoor_drying_basic_time")
		@Config.Name("DryingBasicTime")
		@Config.RangeInt(min = 250, max = 12000)
		public int dryingIndoorsBasicTime = 900;

		@Config.Comment("The ticks of fermentation per item. (1 second = 20 ticks)")
		@Config.LangKey("cultivage.config.flat_basket.fermentation_basic_time")
		@Config.Name("FermentationBasicTime")
		@Config.RangeInt(min = 500, max = 12000)
		public int fermentationBasicTime = 1000;

		@Config.Comment("The ticks of baking per item. (1 second = 20 ticks)")
		@Config.LangKey("cultivage.config.flat_basket.bake_basic_time")
		@Config.Name("DryingBasicTime")
		@Config.RangeInt(min = 250, max = 12000)
		public int BakeBasicTime = 200;
	}
}
