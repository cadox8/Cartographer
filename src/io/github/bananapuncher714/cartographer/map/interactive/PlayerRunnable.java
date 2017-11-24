package io.github.bananapuncher714.cartographer.map.interactive;

import org.bukkit.entity.Player;

public interface PlayerRunnable {
	public boolean run( Player player, Object... objects );
}
