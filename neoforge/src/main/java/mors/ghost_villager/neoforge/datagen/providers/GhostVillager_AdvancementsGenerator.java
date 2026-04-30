package mors.ghost_villager.neoforge.datagen.providers;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class GhostVillager_AdvancementsGenerator extends AdvancementProvider {

    public GhostVillager_AdvancementsGenerator(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new Generator()));
    }

    private static final class Generator implements AdvancementGenerator {
        @Override
        public void generate(final HolderLookup.@NotNull Provider registries, final @NotNull Consumer<AdvancementHolder> consumer, final @NotNull ExistingFileHelper existingFileHelper) {

//            Advancement.Builder.advancement()
//                    .parent(rootAlchemy)
//                    .display(
//                            TCOTS_Items.ALCOHEST.get(), // The display icon
//                            Component.translatable("advancements.witcher.refill_concoction.title"), // The title
//                            Component.translatable("advancements.witcher.refill_concoction.description"), // The description
//                            null, // Background image used
//                            AdvancementType.TASK, // Options: TASK, CHALLENGE, GOAL
//                            true, // Show toast top right
//                            true, // Announce to chat
//                            false // Hidden in the advancement tab
//                    )
//                    .addCriterion(
//                            "refill_concoction",
//                            TCOTS_CustomCriterion.Conditions.createRefillConcoctionCriterion())
//                    .save(consumer, TCOTS_Main.MOD_ID + "/refill_concoction");
        }

    }
}
