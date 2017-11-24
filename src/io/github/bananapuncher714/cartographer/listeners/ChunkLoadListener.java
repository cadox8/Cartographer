package io.github.bananapuncher714.cartographer.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import io.github.bananapuncher714.cartographer.objects.ChunkLocation;

public class ChunkLoadListener implements Listener, Runnable {
	@EventHandler
	public void onChunkLoadEvent( ChunkLoadEvent event ) {
	}

	@Override
	public void run() {
	}

	public static void addChunkToRenderQueue( World world, int x, int z )  {
	}
	
	public static void addChunkToRenderQueue( ChunkLocation location ) {
	}
}
