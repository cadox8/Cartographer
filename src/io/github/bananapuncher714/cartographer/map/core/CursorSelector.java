package io.github.bananapuncher714.cartographer.map.core;

import io.github.bananapuncher714.cartographer.map.RealWorldCursor;

import java.util.List;

import org.bukkit.entity.Player;

public interface CursorSelector {
	public abstract List< RealWorldCursor > getCursors( Player player );
}