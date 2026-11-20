package fr.kamisoris.zeldamod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Bomb extends TntBlock {
    public Bomb(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }
}
