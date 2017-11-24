package io.github.bananapuncher714.waypoints;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.bananapuncher714.cartographer.map.MapText;
import io.github.bananapuncher714.cartographer.map.core.TextSelector;

public class WaypointTextSelector implements TextSelector {
	protected WaypointTextSelector( WaypointAddon addon ) {
	}

	@Override
	public List< MapText > getText( Player player ) {
		return null;
	}
	
	public List< MapText > getSelections( Player player ) {
		return null;
	}
}
