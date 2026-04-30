package mors.ghost_villager.forge.datagen.providers;

import dev.architectury.registry.registries.RegistrySupplier;
import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.registry.GhostVillager_Sounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class GhostVillager_SoundsJsonGenerator extends SoundDefinitionsProvider {

    public GhostVillager_SoundsJsonGenerator(final PackOutput output, final ExistingFileHelper helper) {
        super(output, GhostVillager_Main.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        addSoundEntity(GhostVillager_Sounds.GHOST_VILLAGER_IDLE, 3);

        addSoundEntity(GhostVillager_Sounds.GHOST_VILLAGER_YES, 3);

        addSoundEntity(GhostVillager_Sounds.GHOST_VILLAGER_NO, 3);

        addSoundEntity(GhostVillager_Sounds.GHOST_VILLAGER_POOF, 3);
    }

    private void addSoundEntity(final RegistrySupplier<SoundEvent> sound, final int soundVariationAmount) {
        final SoundDefinition definition = definition().subtitle("subtitles." + GhostVillager_Main.MOD_ID + "." + sound.getId().getPath());

        String[] soundPathSplitted= sound.getId().getPath().split("\\.");

        String mobType  = soundPathSplitted[1];
        String soundType= soundPathSplitted[2];

        String finalPath=mobType+"_"+soundType;

        for (int i = 1; i <= soundVariationAmount; i++)
            definition.with(sound(GhostVillager_Main.MOD_ID + ":entity/" + mobType + "/" + finalPath + i));

        this.add(sound.get(), definition);
    }
}
