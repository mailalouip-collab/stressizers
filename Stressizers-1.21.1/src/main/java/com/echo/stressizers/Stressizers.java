package com.echo.stressizers;

import com.echo.stressizers.content.StressizerContent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Stressizers.MOD_ID)
public class Stressizers {
    public static final String MOD_ID = "stressizers";

    public Stressizers(IEventBus modBus) {
        StressizerContent.BLOCKS.register(modBus);
        StressizerContent.ITEMS.register(modBus);
        StressizerContent.BLOCK_ENTITIES.register(modBus);
        StressizerContent.CREATIVE_TABS.register(modBus);
    }
}
