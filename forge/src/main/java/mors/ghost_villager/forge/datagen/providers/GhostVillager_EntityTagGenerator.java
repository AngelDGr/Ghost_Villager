package mors.ghost_villager.forge.datagen.providers;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.registry.GhostVillager_Entities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GhostVillager_EntityTagGenerator extends EntityTypeTagsProvider {

    public GhostVillager_EntityTagGenerator(final PackOutput arg, final CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable final ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, GhostVillager_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider arg) {
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(GhostVillager_Entities.GHOST_VILLAGER.get());
    }
}