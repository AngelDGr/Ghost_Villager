package mors.ghost_villager.neoforge.datagen;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.neoforge.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GhostVillager_Main.MOD_ID)
public class GhostVillager_DataGenerator {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput output = generator.getPackOutput();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Client
        {
            //Item Model
            generator.addProvider(event.includeClient(), new GhostVillager_ItemModelGenerator(output, existingFileHelper));

            //Sounds
            generator.addProvider(event.includeClient(), new GhostVillager_SoundsJsonGenerator(output, existingFileHelper));

            //Language File
            generator.addProvider(event.includeClient(), new GhostVillager_LanguageFileGenerator(output));
        }


        //Server
        {
            //Tags
            {
                generator.addProvider(event.includeServer(), new GhostVillager_EntityTagGenerator(output, lookupProvider, existingFileHelper));
            }

            //Advancements
            generator.addProvider(event.includeServer(), new GhostVillager_AdvancementsGenerator(output, lookupProvider, existingFileHelper));
        }
    }
}
