package io.github.bananapuncher714.radar;

import java.io.File;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;

public class RadarAddon extends Module {
	@Override
	public void load( BorderedMap map, File dataFolder) {
	}

	@Override
	public void unload() {
	}
	
	public File getDataFolder() {
		return null;
	}
}
