package io.github.bananapuncher714.worldborder;

import java.awt.Color;
import java.io.File;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;

public class WorldBorderAddon extends Module {
	@Override
	public void load( BorderedMap map, File dataFolder) {
	}

	@Override
	public void unload() {
	}

	public boolean isBorder() {
		return false;
	}

	public boolean isShowCenter() {
		return false;
	}

	public Color getBorderColor() {
		return null;
	}

	public double getTint() {
		return 0;
	}
	
	public boolean isHighlight() {
		return false;
	}
}
