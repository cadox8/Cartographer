package io.github.bananapuncher714.worldborder;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor.Type;

import io.github.bananapuncher714.cartographer.map.RealWorldCursor;
import io.github.bananapuncher714.cartographer.map.core.CursorSelector;

public class WorldBorderCursorSelector implements CursorSelector {
	
	WorldBorderCursorSelector( WorldBorderAddon addon, Type center, Type border, double dist ) {
	}

	@Override
	public List< RealWorldCursor > getCursors( Player player ) {
		return null;
	}
}
