package net.starcage.meliorat.common.clock.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.starcage.meliorat.config.MelioratConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClockBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<ClockBlock> CODEC = simpleCodec(ClockBlock::new);
    public static final IntegerProperty TIME = IntegerProperty.create("time",0,3);

    public ClockBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =
            Block.box(
                    1.0, 0.0, 4.0,
                    15.0, 15.0, 13.0
            );

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return SHAPE;
    }

    private void updateClockState(
            BlockState state,
            ServerLevel level,
            BlockPos pos) {

        if (!MelioratConfig.PSEUDO_VANILLA_CLOCK.get()) {

            if (state.getValue(TIME) != 0) {

                level.setBlock(
                        pos,
                        state.setValue(TIME, 0),
                        3
                );
            }

            return;
        }

        long time = level.getDayTime() % 24000;

        int frame;

        if (time < 6000)
            frame = 0;
        else if (time < 12000)
            frame = 1;
        else if (time < 18000)
            frame = 2;
        else
            frame = 3;

        if (state.getValue(TIME) != frame) {

            level.setBlock(
                    pos,
                    state.setValue(TIME, frame),
                    3
            );
        }
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TIME, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TIME);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit) {

    if (player.isShiftKeyDown()) {

        if (!level.isClientSide()) {

            level.removeBlock(pos, false);

            player.addItem(new ItemStack(Items.CLOCK));

        }

        return InteractionResult.SUCCESS;
    }

    return super.useWithoutItem(state, level, pos, player, hit);

    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos) {
        if (direction == Direction.DOWN) {

            if (!canSurvive(state, level, pos)) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(
                state,
                direction,
                neighborState,
                level,
                pos,
                neighborPos
        );
    }

    @Override
    protected boolean canSurvive(
            BlockState state,
            LevelReader level,
            BlockPos pos) {

        BlockPos below = pos.below();

        return level.getBlockState(below)
                .isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    protected void onPlace(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState oldState,
            boolean movedByPiston) {

        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 100);
        }
    }

    @Override
    protected void tick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random) {

        updateClockState(state, level, pos);

        level.scheduleTick(pos, this, 100);
    }
}
