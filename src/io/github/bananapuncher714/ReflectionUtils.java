package io.github.bananapuncher714;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ReflectionUtils {
	
	public static boolean isMainHand( PlayerInteractEvent e ) {
		Method getHand;
		try {
			getHand = e.getClass().getMethod( "getHand" );
			return EquipmentSlot.HAND.equals( getHand.invoke( e ) );
		} catch ( NoSuchMethodException |
				SecurityException |
				IllegalAccessException |
				IllegalArgumentException |
				InvocationTargetException e1 ) {
			return true;
		}
	}
}
