package io.github.bananapuncher714;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Util {
	public static int getWaterDepth( Location l ) {
		Block b = l.getBlock();
		int depth = 1;
		if ( b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER ) return -1;
		Block lower = b.getRelative( BlockFace.DOWN );
		while ( lower.getLocation().getBlockY() > 1 && ( lower.getType() == Material.WATER || lower.getType() == Material.STATIONARY_WATER ) ) {
			depth++;
			lower = lower.getRelative( BlockFace.DOWN );
		}
		return depth;
	}
	
	public static Block getHighestBlockAt( Location l ) {
		return getHighestBlockAt( l, new HashSet< Material >() );
	}
	
	public static Block getHighestBlockAt( Location loc, HashSet< Material > skip ) {
		skip.add( Material.AIR );
		Location l = loc.clone();
		l.setY( loc.getWorld().getMaxHeight() );
		Block b = l.getBlock();
		if ( l.getBlockY() <= 1 ) return b;
		Block upper = b.getRelative( BlockFace.DOWN );
		while ( skip.contains( upper.getType() ) && upper.getLocation().getBlockY() > 1 ) {
			upper = upper.getRelative( BlockFace.DOWN );
		}
		return upper;
	}
}
