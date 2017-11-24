package io.github.bananapuncher714.waypoints;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.bananapuncher714.cartographer.map.RealWorldCursor;
import io.github.bananapuncher714.cartographer.map.core.CursorSelector;

public class WaypointSelector implements CursorSelector {
	public WaypointSelector( WaypointAddon addon ) {
	}

	@Override
	public List<RealWorldCursor> getCursors( Player player ) {
		return null;
	}

}
