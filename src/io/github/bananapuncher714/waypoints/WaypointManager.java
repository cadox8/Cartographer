package io.github.bananapuncher714.waypoints;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WaypointManager {
	public WaypointManager( WaypointAddon addon ) {
	}
	
	public WaypointAddon getAddon() {
		return null;
	}
	
	public void addPublicWaypoint( Waypoint waypoint ) {
	}
	
	public void removePublicWaypoint( Waypoint waypoint ) {
	}
	
	public boolean isPublic( Waypoint waypoint ) {
		return false;
	}
	
	public void setStaff( Waypoint waypoint, boolean staff ) {
	}
	
	public boolean isStaff( Waypoint waypoint ) {
		return false;
	}
	
	public Map< Waypoint, Boolean > getPublicWaypoints() {
		return null;
	}
	
	public Set< UUID > getDiscoverable() {
		return null;
	}
	
	public boolean isDiscoverable( Waypoint waypoint ) {
		return false;
	}
	
	public Waypoint getWaypoint( UUID uuid ) {
		return null;
	}
	
	public Waypoint getFromConfig( FileConfiguration config ) {
		return null;
	}
	
	public void saveWaypoints() {
	}
	
	public void removeWaypoint( Waypoint waypoint ) {
	}
	
	public void addWaypoint( Waypoint waypoint ) {
	}
	
	public Collection< WaypointViewer > getViewers() {
		return null;
	}
	
	public WaypointViewer getViewer( Player player ) {
		return null;
	}
	
	public WaypointViewer getViewer( UUID uuid ) {
		return null;
	}
	
	public WaypointViewer getViewerFromConfig( FileConfiguration config ) {
		return null;
	}
	
	public void saveViewers() {
	}
}