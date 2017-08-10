package io.github.bananapuncher714.map;

import org.bukkit.Location;
import org.bukkit.map.MapCursor;

public class RealWorldCursor {
	private Location l;
	MapCursor.Type type;

	public RealWorldCursor( Location l, MapCursor.Type type ) {
		this.l = l;
		this.type = type;
	}

	public Location getLocation() {
		return l;
	}

	public MapCursor.Type getType() {
		return type;
	}
}