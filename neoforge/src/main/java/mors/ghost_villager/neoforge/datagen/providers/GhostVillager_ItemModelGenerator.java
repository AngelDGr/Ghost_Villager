package mors.ghost_villager.neoforge.datagen.providers;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.GhostVillager_Registries;
import mors.ghost_villager.registry.GhostVillager_Items;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class GhostVillager_ItemModelGenerator extends ItemModelProvider {

    public GhostVillager_ItemModelGenerator(final PackOutput output, final ExistingFileHelper existingFileHelper) {
        super(output, GhostVillager_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.eggItem(GhostVillager_Items.GHOST_VILLAGER_SPAWN_EGG.get());
    }

    protected void eggItem(final Item eggItem) {
        getBuilder(
                Objects.requireNonNull(GhostVillager_Registries.ITEMS.getRegistrar().getId(eggItem)).toString())
                .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}