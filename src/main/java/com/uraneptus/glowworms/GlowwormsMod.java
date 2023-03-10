package com.uraneptus.glowworms;

import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import com.uraneptus.glowworms.core.data.client.GlowwormsBlockStateProvider;
import com.uraneptus.glowworms.core.data.client.GlowwormsItemModelProvider;
import com.uraneptus.glowworms.core.data.client.GlowwormsLangProvider;
import com.uraneptus.glowworms.core.data.server.GlowwormsLootTableProvider;
import com.uraneptus.glowworms.core.data.server.datapack_registries.GlowwormsBiomeModifiersProvider;
import com.uraneptus.glowworms.core.data.server.datapack_registries.GlowwormsConfiguredFeatureProvider;
import com.uraneptus.glowworms.core.data.server.datapack_registries.GlowwormsPlacedFeaturesProvider;
import com.uraneptus.glowworms.core.data.server.tags.GlowwormsBiomeTagsProvider;
import com.uraneptus.glowworms.core.data.server.tags.GlowwormsBlockTagsProvider;
import com.uraneptus.glowworms.core.registry.GlowwormsBlocks;
import com.uraneptus.glowworms.core.registry.GlowwormsItems;
import com.uraneptus.glowworms.core.registry.GlowwormsParticleTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GlowwormsMod.MOD_ID)
@Mod.EventBusSubscriber(modid = GlowwormsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GlowwormsMod {
    public static final String MOD_ID = "glowworms";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modPrefix(String path) {
        return new ResourceLocation(GlowwormsMod.MOD_ID, path);
    }

    public GlowwormsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::gatherData);

        GlowwormsBlocks.BLOCKS.register(bus);
        GlowwormsItems.ITEMS.register(bus);
        GlowwormsParticleTypes.PARTICLES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(includeClient, new GlowwormsBlockStateProvider(generator, fileHelper));
        generator.addProvider(includeClient, new GlowwormsItemModelProvider(generator, fileHelper));
        generator.addProvider(includeClient, new GlowwormsLangProvider(generator));

        GlowwormsBlockTagsProvider blockTagProvider = new GlowwormsBlockTagsProvider(generator, fileHelper);
        generator.addProvider(includeServer, blockTagProvider);
        generator.addProvider(includeServer, new GlowwormsBiomeTagsProvider(generator, fileHelper));
        generator.addProvider(includeServer, new GlowwormsLootTableProvider(generator));
        generator.addProvider(includeServer, GlowwormsBiomeModifiersProvider.createBiomeModifiers(generator, fileHelper));
        generator.addProvider(includeServer, GlowwormsConfiguredFeatureProvider.createConfiguredFeatures(generator, fileHelper));
        generator.addProvider(includeServer, GlowwormsPlacedFeaturesProvider.createPlacedFeatures(generator, fileHelper));

/*
        generator.addProvider(includeServer, new SMItemTagsProvider(generator, blockTagProvider, fileHelper));
        generator.addProvider(includeServer, new SMAdvancementProvider(generator, fileHelper));
        generator.addProvider(includeServer, new SMRecipeProvider(generator));
        SMDatapackRegistryProviders.registerDatapackProviders(fileHelper, generator, registryOps);

 */
    }
}
