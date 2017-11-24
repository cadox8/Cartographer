package io.github.bananapuncher714.cartographer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.map.MapView.Scale;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;

public class PlayerChangeMapEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final BorderedMap originalMap, newMap;
	private final Player player;
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}

	public BorderedMap getOriginalMap() {
		return originalMap;
	}

	public BorderedMap getNewMap() {
		return newMap;
	}

	public Player getPlayer() {
		return player;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

	public PlayerChangeMapEvent( Player player, BorderedMap original, BorderedMap newMap ) {
		this.player = player;
		originalMap = original;
		this.newMap = newMap;
	}
}
