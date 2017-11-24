package io.github.bananapuncher714.cartographer.map.core;

import io.github.bananapuncher714.cartographer.map.MapText;

import java.util.List;

import org.bukkit.entity.Player;

public interface TextSelector {
	public abstract List< MapText > getText( Player player );
}