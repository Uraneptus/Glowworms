package com.uraneptus.glowworms.core.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.uraneptus.glowworms.core.registry.GlowwormsBlocks;
import com.uraneptus.glowworms.core.registry.GlowwormsItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GlowwormsLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> lootTables = ImmutableList.of(Pair.of(SMBlockLoot::new, LootContextParamSets.BLOCK));

    public GlowwormsLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.lootTables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    }

    private static class SMBlockLoot extends BlockLoot {
        @Override
        protected void addTables() {
            this.dropSelf(GlowwormsBlocks.GLOWWORMS.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return GlowwormsBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
