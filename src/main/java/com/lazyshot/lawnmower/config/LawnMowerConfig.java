package com.lazyshot.lawnmower.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public final class LawnMowerConfig {

    public static ForgeConfigSpec.BooleanValue enableRiding;
    public static ForgeConfigSpec.BooleanValue enablePowered;
    public static ForgeConfigSpec.ConfigValue<List<String>> mowedBlocks;

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    static {
        COMMON_BUILDER.comment("General Settings").push("general");

        enableRiding = COMMON_BUILDER
                .comment("Enable riding lawn mower")
                .define("enableRiding", false);

        enablePowered = COMMON_BUILDER
                .comment("Enable powered lawn mower")
                .define("enablePowered", false);

        mowedBlocks = COMMON_BUILDER
                .comment("Blocks mowed by mowers")
                .define("mowedBlocks", new ArrayList<String>());

        COMMON_BUILDER.pop();

        SPEC = COMMON_BUILDER.build();
    }
}
