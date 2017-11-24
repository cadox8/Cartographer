package io.github.bananapuncher714.waypoints.file;

import java.io.File;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.bananapuncher714.waypoints.Waypoint;
import io.github.bananapuncher714.waypoints.WaypointAddon;
import io.github.bananapuncher714.waypoints.WaypointManager;
import io.github.bananapuncher714.waypoints.WaypointViewer;

public final class WaypointFileManager {

	public static void saveViewer( WaypointAddon addon, WaypointViewer viewer ) {
	}
	
	public static WaypointViewer loadViewer( WaypointAddon addon, Player player ) {
		return null;
	}
	
	public static WaypointViewer loadViewer( WaypointAddon addon, UUID uuid ) {
		return null;
	}
	
	public static WaypointViewer loadViewer( WaypointManager manager, File file ) {
		return null;
	}
	
	public static void saveWaypoint( WaypointAddon addon, Waypoint waypoint ) {
	}
	
	public static Waypoint loadWaypoint( WaypointAddon addon, File file ) {
		return null;
	}
	
	public static Waypoint loadWaypoint( WaypointAddon addon, UUID uuid ) {
		return null;
	}
	
	public static void removeWaypoint( WaypointAddon addon, Waypoint waypoint ) {
	}
	
	public static Location getLocationFromString( String string ) {
		return null;
	}
	
	public static String getStringFromLocation( Location location ) {
		return null;
	}
	
	public static void refresh( WaypointAddon addon ) {
	}
}
