package com.lazyshot.lawnmower;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.lazyshot.lawnmower.config.LawnMowerConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LawnMower.MODID)
public class LawnMower {
    public static final String MODID = "lawnmower";
    private static final Logger LOGGER = LogManager.getLogger(LawnMower.class);

    public LawnMower() {
        LOGGER.debug("Loaded lawnmower mod!");

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, LawnMowerConfig.SPEC);

        CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        LOGGER.debug("Loaded config: {}", configData.toString());
        LawnMowerConfig.SPEC.setConfig(configData);
    }
}
