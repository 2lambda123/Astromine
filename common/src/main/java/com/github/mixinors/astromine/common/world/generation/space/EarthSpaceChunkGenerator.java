/*
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mixinors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mixinors.astromine.common.world.generation.space;

import com.github.mixinors.astromine.registry.common.AMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import com.github.mixinors.astromine.common.noise.OctaveNoiseSampler;
import com.github.mixinors.astromine.common.noise.OpenSimplexNoise;
import com.github.mixinors.astromine.registry.common.AMConfig;

import java.util.Arrays;
import java.util.Random;

public class EarthSpaceChunkGenerator extends ChunkGenerator {
	public static Codec<EarthSpaceChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.LONG.fieldOf("seed").forGetter(gen -> gen.seed), RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry)).apply(instance,
		EarthSpaceChunkGenerator::new));

	private final long seed;
	private final Registry<Biome> biomeRegistry;

	private final OctaveNoiseSampler<OpenSimplexNoise> noise;

	public EarthSpaceChunkGenerator(long seed, Registry<Biome> biomeRegistry) {
		super(new EarthSpaceBiomeSource(biomeRegistry, seed), new StructuresConfig(false));
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;
		this.noise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, new Random(seed), 3, 200, 1.225, 1);
	}

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return withSeedCommon(seed);
	}

	public ChunkGenerator withSeedCommon(long seed) {
		return new EarthSpaceChunkGenerator(seed, biomeRegistry);
	}

	@Override
	public void buildSurface(ChunkRegion region, Chunk chunk) {
	
	}

	@Override
	public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
		var mutable = new BlockPos.Mutable();
		
		var x1 = chunk.getPos().getStartX();
		var z1 = chunk.getPos().getStartZ();
		var y1 = 0;

		var x2 = chunk.getPos().getEndX();
		var z2 = chunk.getPos().getEndZ();
		var y2 = 256;

		var random = new ChunkRandom();
		random.setPopulationSeed(this.seed, x1, z1);

		for (var x = x1; x <= x2; ++x) {
			for (var z = z1; z <= z2; ++z) {
				for (var y = y1; y <= y2; ++y) {
					var noise = this.noise.sample(x, y, z);
					noise -= computeNoiseFalloff(y);

					if (noise > AMConfig.get().asteroidNoiseThreshold) {
						if (random.nextInt(64) != 0) {
							chunk.setBlockState(mutable.set(x, y, z), AMBlocks.ASTEROID_STONE.get().getDefaultState(), false);
						}
					}
				}
			}
		}
	}

	// Desmos: \frac{10}{x+1}-\frac{10}{x-257}-0.155
	// It should actually be 10/y - 10/(y - 256) but i don't want to divide by 0 today
	private double computeNoiseFalloff(int y) {
		return (10.0D / (y + 1.0D)) - (10.0D / (y - 257.0D)) - 0.155D;
	}

	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmapType) {
		return 0;
	}

	@Override
	public BlockView getColumnSample(int x, int z) {
		var states = new BlockState[256];
		
		Arrays.fill(states, Blocks.AIR.getDefaultState());
		
		return new VerticalBlockSample(states);
	}
}
