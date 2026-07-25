package com.arteoformance.apocabuckets.neoforge;

import com.arteoformance.apocabuckets.neoforge.registry.NeoForgePlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import com.arteoformance.apocabuckets.ApocaBuckets;

@Mod(ApocaBuckets.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus modBus) {
        // Run our common setup.
        ApocaBuckets.initRegistries(new NeoForgePlatform());
        ApocaBuckets.init();
        NeoForgePlatform.ITEMS.register(modBus);
        NeoForgePlatform.BLOCKS.register(modBus);
        NeoForgePlatform.FLUID_TYPES.register(modBus);
        NeoForgePlatform.FLUIDS.register(modBus);
        NeoForgePlatform.CREATIVE_TABS.register(modBus);
    }
}
