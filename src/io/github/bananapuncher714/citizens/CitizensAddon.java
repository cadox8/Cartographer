package io.github.bananapuncher714.citizens;

import java.io.File;

import org.bukkit.map.MapCursor.Type;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;

public class CitizensAddon extends Module {
	
	@Override
	public void load( BorderedMap map, File dataFolder ) {
	}

	public File getDatafolder() {
		return null;
	}

	public boolean isHidden() {
		return false;
	}

	public boolean isVisible() {
		return false;
	}

	public double getRange() {
		return 0;
	}

	public Type getType() {
		return null;
	}
	
	public CursorManager getManager() {
		return null;
	}

	@Override
	public void unload() {
	}
}
