package cloud.lemonslice.afterthedrizzle.data.provider;

import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.data.tag.NormalTags;
import com.google.common.collect.Lists;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;

import java.util.List;

public class NormalFluidTagsProvider extends FluidTagsProvider
{
    public NormalFluidTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        getBuilder(NormalTags.Fluids.DRINK).add(FluidsRegistry.BOILING_WATER_STILL.get(), FluidsRegistry.SUGARY_WATER_STILL.get(), FluidsRegistry.WEAK_BLACK_TEA_STILL.get(), FluidsRegistry.BLACK_TEA_STILL.get(), FluidsRegistry.STRONG_BLACK_TEA_STILL.get(),
                FluidsRegistry.WEAK_GREEN_TEA_STILL.get(), FluidsRegistry.GREEN_TEA_STILL.get(), FluidsRegistry.STRONG_GREEN_TEA_STILL.get(),
                FluidsRegistry.WEAK_WHITE_TEA_STILL.get(), FluidsRegistry.WHITE_TEA_STILL.get(), FluidsRegistry.STRONG_WHITE_TEA_STILL.get());

        List<Fluid> water = Lists.newArrayList();
        FluidsRegistry.FLUIDS.getEntries().forEach(fluid -> water.add(fluid.get()));
        getBuilder(FluidTags.WATER).add(water.toArray(new Fluid[0]));
    }

    @Override
    public String getName()
    {
        return "After the Drizzle Fluid Tags";
    }
}
