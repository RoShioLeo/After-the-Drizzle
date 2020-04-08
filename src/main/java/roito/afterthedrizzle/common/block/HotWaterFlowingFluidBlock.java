package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class HotWaterFlowingFluidBlock extends NormalFlowingFluidBlock
{
    public static final DamageSource BOILING = new DamageSource("boiling");

    public HotWaterFlowingFluidBlock(String name, Supplier<? extends FlowingFluid> supplier)
    {
        super(name, supplier, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops().tickRandomly());
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = pos.getZ() + 0.5D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        if (this.getFluid().getAttributes().getTemperature() >= 373)
        {
            worldIn.addParticle(ParticleTypes.BUBBLE, false, d0 + d4, d1, d2 + d4, 0.0D, 0.5D, 0.0D);
        }
        if ((this.getFluid().getAttributes().getTemperature() - 273) / 100F >= rand.nextFloat())
        {
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 1.25D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof LivingEntity)
        {
            if (this.getFluid().getAttributes().getTemperature() >= 373)
            {
                entityIn.attackEntityFrom(BOILING, 6.0F);
            }
            else if (this.getFluid().getAttributes().getTemperature() >= 353)
            {
                entityIn.attackEntityFrom(BOILING, 4.0F);
            }
            else if (this.getFluid().getAttributes().getTemperature() >= 318)
            {
                entityIn.attackEntityFrom(BOILING, 2.0F);
            }
            else
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.REGENERATION, 40, 0));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random)
    {
        if (state.getBlock() == BlocksRegistry.WARM_WATER && worldIn.getBlockState(pos.down(2)).getBlock() instanceof CampfireBlock)
        {
            return;
        }
        else if (state.getFluidState().getLevel() == 8 && random.nextInt(10) == 0)
        {
            if (state.getFluidState().getFluid().getAttributes().getTemperature() >= 373)
            {
                worldIn.setBlockState(pos, BlocksRegistry.HOT_WATER_80.getDefaultState());
            }
            else if (state.getFluidState().getFluid().getAttributes().getTemperature() >= 353)
            {
                worldIn.setBlockState(pos, BlocksRegistry.HOT_WATER_60.getDefaultState());
            }
            else if (state.getFluidState().getFluid().getAttributes().getTemperature() >= 318)
            {
                worldIn.setBlockState(pos, BlocksRegistry.WARM_WATER.getDefaultState());
            }
            else
            {
                worldIn.setBlockState(pos, BlocksRegistry.WATER.getDefaultState());
            }
        }
    }
}
