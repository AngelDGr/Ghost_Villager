package mors.ghost_villager.forge.datagen.providers;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.GhostVillager_Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GhostVillager_LanguageFileGenerator extends LanguageProvider {

    public GhostVillager_LanguageFileGenerator(final PackOutput output) {
        super(output, GhostVillager_Main.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        GhostVillager_Registries.ENTITY_TYPES.getRegistrar().entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(GhostVillager_Main.MOD_ID))
                .forEach((entry)->addEntityTranslation(entry.getKey()));

        GhostVillager_Registries.ITEMS.getRegistrar().entrySet().stream()
                .filter(entry ->
                        entry.getKey().location().getNamespace().equals(GhostVillager_Main.MOD_ID)
                        && !(entry.getValue() instanceof BlockItem && !(entry.getValue() instanceof ItemNameBlockItem)))
                .forEach((entry)-> addItemTranslation(entry.getKey()));

        //Subtitles
        {
            add("subtitles.ghost_villager.entity.ghost_villager.idle", "Ghost Villager mumbles");

            add("subtitles.ghost_villager.entity.ghost_villager.yes", "Ghost Villager agrees");

            add("subtitles.ghost_villager.entity.ghost_villager.no", "Ghost Villager disagrees");

            add("subtitles.ghost_villager.entity.ghost_villager.poof", "Ghost Villager disappears");
        }
    }

    private void addEntityTranslation(ResourceKey<EntityType<?>> item) {
        String path = item.location().getPath();
        String translation = translate(path);
        add(BuiltInRegistries.ENTITY_TYPE.get(item.location()), translation);
    }

    private void addItemTranslation(ResourceKey<Item> item) {
        String path = item.location().getPath();
        String translation = translate(path);

        //Handles descriptions and names for banner patterns
        if(BuiltInRegistries.ITEM.get(item.location()) instanceof BannerPatternItem bannerPatternItem){
            String resourceKeyPath= bannerPatternItem.getBannerPattern().location().getPath();
            String[] resourceKeySplitted = resourceKeyPath.split("/");
            //Name (All are named "Banner Pattern")
            add(BuiltInRegistries.ITEM.get(item.location()), "Banner Pattern");

            String translationBanner = translate(resourceKeySplitted[1]);
            //Description
            add(BuiltInRegistries.ITEM.get(item.location()).getDescriptionId()+".desc", translationBanner);

        } else {
            add(BuiltInRegistries.ITEM.get(item.location()), translation);
        }
    }

    private String translate(String path) {
        return Arrays.stream(path.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
