package com.uraneptus.glowworms.core.other.tags;

import com.uraneptus.glowworms.GlowwormsMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class GlowwormsBiomeTags {
    public static final TagKey<Biome> GLOWWORMS_GENERATE_IN = TagKey.create(Registry.BIOME_REGISTRY, GlowwormsMod.modPrefix("glowworms_generate_in"));
}
