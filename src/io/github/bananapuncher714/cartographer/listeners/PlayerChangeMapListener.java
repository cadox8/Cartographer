package io.github.bananapuncher714.cartographer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle switching maps automatically when a player moves;
 * Keep the current map only if:
 * 1. They're still on it
 * 2. It's in the same world as them and no other maps match
 * 
 * @author BananaPuncher714 
 */
public class PlayerChangeMapListener implements Listener {
	
	@EventHandler
	public void onPlayerMoveEvent( PlayerMoveEvent event ) {
	}
}
