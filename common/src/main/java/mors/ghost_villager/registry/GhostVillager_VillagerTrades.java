package mors.ghost_villager.registry;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;

public class GhostVillager_VillagerTrades {

    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> GHOST_VILLAGER_TRADES =
            toIntMap(ImmutableMap.of(
                    //    Level 1 – Novice
                    //1–2 emerald blocks → 4–6 blaze rods
                    //2–3 emerald blocks → 3–6 ender pearls
                    //2–3 emerald blocks → 1–2 ghast tears
                    1, new VillagerTrades.ItemListing[]{
                            new ItemsForRandomEmeraldBlocks(Items.BLAZE_ROD, UniformInt.of(1, 2), UniformInt.of(4, 6), 12, 2, 0.2F),
                            new ItemsForRandomEmeraldBlocks(Items.ENDER_PEARL, UniformInt.of(2, 3), UniformInt.of(3, 6), 12, 2, 0.2F),
                            new ItemsForRandomEmeraldBlocks(Items.GHAST_TEAR, UniformInt.of(2, 3), UniformInt.of(1, 2), 12, 2, 0.2F)
                    },
                    //    Level 2 – Apprentice
                    //4–6 emerald blocks → Efficiency V book
                    //4–6 emerald blocks → Protection IV book
                    2, new VillagerTrades.ItemListing[]{
                            new EnchantBookForEmeraldBlocks(30, 4, 0.2f, UniformInt.of(4, 6), Enchantments.BLOCK_EFFICIENCY, 5),
                            new EnchantBookForEmeraldBlocks(30, 4, 0.2f, UniformInt.of(4, 6), Enchantments.ALL_DAMAGE_PROTECTION, 4)
                    },
                    //    Level 3 – Journeyman
                    //6–8 emerald blocks → Sharpness V book
                    //6–8 emerald blocks → Mending book
                    3, new VillagerTrades.ItemListing[]{
                            new EnchantBookForEmeraldBlocks(40, 4, 0.25f, UniformInt.of(6, 8), Enchantments.SHARPNESS, 5),
                            new EnchantBookForEmeraldBlocks(40, 4, 0.25f, UniformInt.of(6, 8), Enchantments.MENDING, 1)
                    },
                    //    Level 4 – Expert
                    //8–12 emerald blocks → Totem of Undying
                    //10–14 emerald blocks → Shulker Box
                    4, new VillagerTrades.ItemListing[]{
                            new ItemsForRandomEmeraldBlocks(Items.TOTEM_OF_UNDYING, UniformInt.of(8, 12), 1, 3, 50, 0.5F),
                            new ItemsForRandomEmeraldBlocks(Items.SHULKER_BOX, UniformInt.of(10, 14), 1, 3, 50, 0.5F)
                    },
                    //    Level 5 – Master
                    //20–32 emerald blocks → Elytra
                    //24–40 emerald blocks → Nether Star
                    5, new VillagerTrades.ItemListing[]{
                            new ItemsForRandomEmeraldBlocks(Items.ELYTRA, UniformInt.of(20, 32), 1, 2, 100, 1.5F),
                            new ItemsForRandomEmeraldBlocks(Items.NETHER_STAR, UniformInt.of(20, 42), 1, 1, 100, 1.5F)
                    }
            ));

    @SuppressWarnings({"unused"})
    static class ItemsForRandomEmeraldBlocks implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final UniformInt emeraldCost;
        private final UniformInt stackCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForRandomEmeraldBlocks(Item item, UniformInt emeraldCost, UniformInt count, int maxUses, int villagerXp) {
            this(new ItemStack(item), emeraldCost, count, maxUses, villagerXp);
        }

        public ItemsForRandomEmeraldBlocks(ItemStack itemStack, UniformInt emeraldCost, UniformInt count, int maxUses, int villagerXp) {
            this(itemStack, emeraldCost, count, maxUses, villagerXp, 0.05F);
        }

        public ItemsForRandomEmeraldBlocks(Item item, UniformInt emeraldCost, int count, int maxUses, int villagerXp, float priceMultiplier) {
            this(new ItemStack(item), emeraldCost, UniformInt.of(count, count), maxUses, villagerXp, priceMultiplier);
        }

        public ItemsForRandomEmeraldBlocks(Item item, UniformInt emeraldCost, UniformInt count, int maxUses, int villagerXp, float priceMultiplier) {
            this(new ItemStack(item), emeraldCost, count, maxUses, villagerXp, priceMultiplier);
        }

        public ItemsForRandomEmeraldBlocks(ItemStack itemStack, UniformInt emeraldCost, UniformInt count, int maxUses, int villagerXp,
                                           float priceMultiplier) {
            this.itemStack = itemStack;
            this.emeraldCost = emeraldCost;
            this.stackCount = count;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
            ItemStack itemStack = this.itemStack.copy();
            itemStack.setCount(this.stackCount.sample(randomSource));
            Level level = entity.level();


            return new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, this.emeraldCost.sample(randomSource)), itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> immutableMap) {
        return new Int2ObjectOpenHashMap<>(immutableMap);
    }

    static class EnchantBookForEmeraldBlocks implements VillagerTrades.ItemListing {
        private final int villagerXp;
        private final int maxUses;
        private final float priceMultiplier;
        private final Enchantment enchantment;
        private final UniformInt emeraldAmount;
        private final int enchantmentLevel;

        public EnchantBookForEmeraldBlocks(int villagerXp, int maxUses, float priceMultiplier, UniformInt emeraldAmount, Enchantment enchantment, int enchantmentLevel) {
            this.villagerXp = villagerXp;
            this.maxUses = maxUses;
            this.priceMultiplier = priceMultiplier;
            this.enchantmentLevel = enchantmentLevel;
            this.enchantment = enchantment;
            this.emeraldAmount = emeraldAmount;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
            ItemStack itemStack;
            if (enchantment!=null) {
                itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, this.enchantmentLevel));
            } else {
                itemStack = new ItemStack(Items.BOOK);
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, this.emeraldAmount.sample(randomSource)),
                    new ItemStack(Items.BOOK), itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }
}
