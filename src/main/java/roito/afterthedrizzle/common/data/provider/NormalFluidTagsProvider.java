package roito.afterthedrizzle.common.data.provider;

import com.google.common.collect.Lists;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;

import java.util.List;

import static roito.afterthedrizzle.common.data.tag.NormalTags.Fluids.DRINK;
import static roito.afterthedrizzle.common.fluid.FluidsRegistry.*;

public class NormalFluidTagsProvider extends FluidTagsProvider
{
    public NormalFluidTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        getBuilder(DRINK).add(SUGARY_WATER_STILL.get(), WEAK_BLACK_TEA_STILL.get(), BLACK_TEA_STILL.get(), STRONG_BLACK_TEA_STILL.get(),
                WEAK_GREEN_TEA_STILL.get(), GREEN_TEA_STILL.get(), STRONG_GREEN_TEA_STILL.get(),
                WEAK_WHITE_TEA_STILL.get(), WHITE_TEA_STILL.get(), STRONG_WHITE_TEA_STILL.get());

        List<Fluid> water = Lists.newArrayList();
        FLUIDS.getEntries().forEach(fluid -> water.add(fluid.get()));
        getBuilder(FluidTags.WATER).add(water.toArray(new Fluid[0]));
    }

    @Override
    public String getName()
    {
        return "After the Drizzle Fluid Tags";
    }
}
