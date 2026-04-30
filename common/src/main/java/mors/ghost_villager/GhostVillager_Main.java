package mors.ghost_villager;

import com.mojang.logging.LogUtils;
import mors.ghost_villager.registry.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

public final class GhostVillager_Main {
    public static final String MOD_ID = "ghost_villager";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        // Write common init code here.

        //Entities
        GhostVillager_Entities.init(); GhostVillager_Registries.ENTITY_TYPES.register();

        //Items
        GhostVillager_Items.init(); GhostVillager_Registries.ITEMS.register();

        //Sounds
        GhostVillager_Sounds.init(); GhostVillager_Registries.SOUNDS.register();
    }
}
