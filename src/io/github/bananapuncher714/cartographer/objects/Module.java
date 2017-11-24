package io.github.bananapuncher714.cartographer.objects;

import java.io.File;

import io.github.bananapuncher714.cartographer.map.core.BorderedMap;

public abstract class Module {
	public abstract void load( BorderedMap map, File dataFolder );
	public abstract void unload();
}
