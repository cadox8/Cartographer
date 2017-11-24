package io.github.bananapuncher714.worldguard;

import java.awt.Color;

import org.bukkit.Location;

import io.github.bananapuncher714.cartographer.map.core.MapShader;

public class WorldGuardLandShader implements MapShader {
	public WorldGuardLandShader( Color tint ) {
	}

	@Override
	public Color shadeLocation( Location location, Color color ) {
		return null;
	}
}
