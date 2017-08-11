package io.github.bananapuncher714.map;

import org.bukkit.Location;
import org.bukkit.map.MapCursor;

public class RealWorldCursor {
	private Location l;
	MapCursor.Type type;
	boolean oob;

	public RealWorldCursor( Location l, MapCursor.Type type, boolean hideWhenOOB ) {
		this.l = l;
		this.type = type;
		oob = hideWhenOOB;
	}

	public Location getLocation() {
		return l;
	}

	public MapCursor.Type getType() {
		return type;
	}
	
	public boolean hideWhenOOB() {
		return oob;
	}
}