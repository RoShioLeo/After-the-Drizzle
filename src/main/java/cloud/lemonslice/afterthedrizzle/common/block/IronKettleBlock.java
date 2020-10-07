package cloud.lemonslice.afterthedrizzle.common.block;

import cloud.lemonslice.afterthedrizzle.common.fluid.FluidsRegistry;
import cloud.lemonslice.afterthedrizzle.common.tileentity.TeapotTileEntity;
import cloud.lemonslice.afterthedrizzle.common.tileentity.TileEntityTypesRegistry;
import cloud.lemonslice.silveroak.helper.VoxelShapeHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class IronKettleBlock extends TeapotBlock
{
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(2, 0, 2, 12, 11, 12);

    public IronKettleBlock(String name, Properties properties)
    {
        super(name, properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return TileEntityTypesRegistry.IRON_KETTLE.create();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = pos.getZ() + 0.5D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TeapotTileEntity && ((TeapotTileEntity) tileentity).getFluid() == FluidsRegistry.BOILING_WATER_STILL.get())
        {
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 0.5D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        entityIn.onLivingFall(fallDistance, 1.0F);
    }
}
