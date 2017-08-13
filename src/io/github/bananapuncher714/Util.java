/**
 * Handles simple tests like getting the highest distance at a location and getting the depth of water.
 * 
 * @author BananaPuncher714
 */
package io.github.bananapuncher714;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Util {
	/**
	 * Gets how deep the water is in blocks from a certain location.
	 * 
	 * @param l
	 * The location to test at
	 * @return
	 * The amount of water blocks below the location including the location itself
	 */
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
	
	/**
	 * Gets the highest block at a certain location
	 * 
	 * @param l
	 * The location to get a block
	 * @return
	 * The highest block including transparent objects.
	 */
	public static Block getHighestBlockAt( Location l ) {
		return getHighestBlockAt( l, new HashSet< Material >() );
	}
	
	/**
	 * Gets the highest block at a certain location while skipping certain materials.
	 * 
	 * @param loc
	 * The location to get a block
	 * @param skip
	 * The set of materials to skip
	 * @return
	 * The highest block at a certain location without being in the set of transparent blocks.
	 */
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
