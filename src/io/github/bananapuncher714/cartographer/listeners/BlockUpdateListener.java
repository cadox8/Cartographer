package io.github.bananapuncher714.cartographer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import io.github.bananapuncher714.cartographer.CartographerMain;

public class BlockUpdateListener implements Listener {
	CartographerMain main = CartographerMain.getMain();
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onBlockBreakEvent( BlockBreakEvent e ) {
	}
	
	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void onBlockBreakEvent( BlockPlaceEvent e ) {
	}
}
