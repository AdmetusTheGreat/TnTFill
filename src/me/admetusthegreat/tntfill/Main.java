package me.admetusthegreat.tntfill;

import me.admetusthegreat.tntfill.commands.TnTFill;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override 
	public void onEnable() {
		
		getCommand("tntfill").setExecutor(new TnTFill(this));
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
}
