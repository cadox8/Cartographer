package io.github.bananapuncher714.waypoints;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor.Type;

public class Waypoint {
	
	public Waypoint( WaypointManager manager, Player player, String name, Location location, boolean teleport ) {
	}
	
	public UUID getCreator() {
		return null;
	}
	
	public String getName() {
		return null;
	}
	
	public String getCreatorName() {
		return null;
	}
	
	public UUID getId() {
		return null;
	}
	
	public void setType( Type type ) {
	}
	
	public Type getType() {
		return null;
	}
	
	public Location getLocation() {
		return null;
	}
	
	public boolean teleportable() {
		return false;
	}
	
	public void setDiscover( double range ) {
	}
	
	public double getRange() {
		return 0;
	}
	
	public boolean isDiscoverable() {
		return false;
	}
	
	public void remove() {
	}
	
	public Set< UUID > getShared() {
		return null;
	}
}