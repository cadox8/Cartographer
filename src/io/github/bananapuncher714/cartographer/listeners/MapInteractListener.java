package io.github.bananapuncher714.cartographer.listeners;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;

public class MapInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteractEvent( PlayerInteractEvent e ) {
	}
	
	@EventHandler
	public void onMapInitializeEvent( MapInitializeEvent e ) {
	}
	
	public static Set< Short > getMapIds() {
		return null;
	}
	
	public static void addMapId( short id ) {
	}
}
