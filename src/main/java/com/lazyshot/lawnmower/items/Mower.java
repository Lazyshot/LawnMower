package com.lazyshot.lawnmower.items;

import com.lazyshot.lawnmower.LawnMower;
import com.lazyshot.lawnmower.config.LawnMowerConfig;
import com.lazyshot.lawnmower.init.ModItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mower extends Item {
    private static Logger LOGGER = LogManager.getLogger(Mower.class);
    private static int durability = 1500;
    private int radius = 1;
    private int depth = 3;
    private Set<String> mowableBlocks;

    public Mower() {
        super(new Item.Properties().maxDamage(durability).group(ModItemGroups.MOD_ITEM_GROUP));
        mowableBlocks = new HashSet<>(LawnMowerConfig.mowedBlocks.get());
    }

    public Mower(int radius, int depth) {
        this();
        this.radius = radius;
        this.depth = depth;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote) {
            BlockPos startPos = player.getPosition();
            List<BlockPos> coords = new ArrayList<>();
            Direction facing = player.getHorizontalFacing();
            Direction rotated = facing.rotateY();

            for (int i = -1 * radius; i <= radius; i++) {
                for (int j = 1; j <= depth; j++) {
                    coords.add(startPos.offset(facing, j).offset(rotated, i));
                }
            }


            for (BlockPos pos : coords) {
                BlockState state = world.getBlockState(pos);
//                String blockName = state.getBlock().getRegistryName().toString();

                if (state.isAir(world, pos))
                    continue;

                if (state.getBlockHardness(world, pos) == 0) {
                    world.destroyBlock(pos, true);
                }
            }

            return new ActionResult<>(ActionResultType.PASS, itemStack);
        }

        player.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.PASS, itemStack);
    }
}
