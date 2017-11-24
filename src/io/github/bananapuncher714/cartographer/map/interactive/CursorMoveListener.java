package io.github.bananapuncher714.cartographer.map.interactive;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CursorMoveListener implements Listener, Runnable {
	@Override
	public void run() {
	}
	
	@EventHandler
	public void onPlayerChangeItem( PlayerItemHeldEvent event ) {
	}

	@EventHandler
	public void onPlayerDropItemEvent( PlayerDropItemEvent event ) {
	}
}
