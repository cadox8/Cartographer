package io.github.bananapuncher714.worldborder;

import java.awt.Color;

import org.bukkit.Location;

import io.github.bananapuncher714.cartographer.map.core.MapShader;

public class WorldBorderShader implements MapShader {
	
	public WorldBorderShader( WorldBorderAddon addon ) {
	}
	
	@Override
	public Color shadeLocation( Location location, Color color ) {
		return null;
	}
}
