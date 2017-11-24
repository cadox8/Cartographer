package io.github.bananapuncher714.kingdoms;

import java.io.File;

import org.bukkit.map.MapCursor.Type;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;

public class KingdomsAddon extends Module {
	@Override
	public void load( BorderedMap map, File dataFolder) {
	}

	@Override
	public void unload() {
	}
	
	public boolean unloaded() {
		return false;
	}
	
	public Type getMemberCursorType() {
		return null;
	}
	
	public Type getKingCursorType() {
		return null;
	}
	
	public Type getModCursorType() {
		return null;
	}
	
	public Type getHomeCursorType() {
		return null;
	}
	
	public double getShadingPercent() {
		return 0;
	}
	
	public BorderedMap getMap() {
		return null;
	}
}
