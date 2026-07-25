package com.arteoformance.apocabuckets;

import com.arteoformance.apocabuckets.registry.AbstractPlatform;
import net.minecraft.world.item.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApocaBuckets {
    public static final String MOD_ID = "apocabuckets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        ApocaItems.init();
        ApocaBlocks.init();
        ApocaFluids.init();
    }

    public static AbstractPlatform REGISTRY;

    public static void initRegistries(AbstractPlatform registry) {
        REGISTRY = registry;
    }

    public static DyeColor getClosestDyeTo(int color) {
        LOGGER.info(String.format("Getting closest dye for color %s", Integer.toHexString(color)));
        int targetR = color >> 16 & 0xFF;
        int targetG = color >> 8 & 0xFF;
        int targetB = color & 0xFF;
        DyeColor result = DyeColor.BLACK;
        // Max proximity distance, 255^2 * 3
        int proximity = 195075;
        for (DyeColor dyeColor : DyeColor.values()) {
            int dye = dyeColor.getTextureDiffuseColor();
            int dyeR = dye >> 16 & 0xFF;
            int dyeG = dye >> 8 & 0xFF;
            int dyeB = dye & 0xFF;
            int thisProximity = (targetR-dyeR)*(targetR-dyeR) + (targetG-dyeG)*(targetG-dyeG) + (targetB-dyeB)*(targetB-dyeB);
            if (thisProximity < proximity) {
                result = dyeColor;
                proximity = thisProximity;
            }
        }
        LOGGER.info(String.format("Closest dye for color %s is %s (%s)", Integer.toHexString(color), result.getName(), Integer.toHexString(result.getTextureDiffuseColor())));
        return result;
    }
}
