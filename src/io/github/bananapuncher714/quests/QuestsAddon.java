package io.github.bananapuncher714.quests;

import java.io.File;

import org.bukkit.map.MapCursor.Type;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;
import io.github.bananapuncher714.cartographer.objects.Module;

public class QuestsAddon extends Module {
	@Override
	public void load( BorderedMap map, File dataFolder ) {
	}

	@Override
	public void unload() {
	}

	public Type getLoc() {
		return null;
	}

	public Type getInteract() {
		return null;
	}

	public Type getKill() {
		return null;
	}

	public boolean isHighlight() {
		return false;
	}
}
