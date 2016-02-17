package me.admetusthegreat.tntfill.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.admetusthegreat.tntfill.Main;

public class TnTFill implements CommandExecutor {

	public Main plugin;

	public TnTFill(Main main) {

		this.plugin = main;

	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String cl, String[] args) {

		if(cl.equalsIgnoreCase("tntfill")) {

			if(s instanceof Player) {

				Player p = (Player) s;
				
				if(p.hasPermission(plugin.getConfig().getString("permission-node"))) {

					if(p.getInventory().contains(Material.TNT)) {

						Block b = p.getWorld().getBlockAt(p.getLocation());
						List<Dispenser> dispensers = new ArrayList<Dispenser>();

						Block br;

						for (int x = -plugin.getConfig().getInt("x-int"); x <= plugin.getConfig().getInt("x-int"); x++) {

							for (int y = -plugin.getConfig().getInt("y-int"); y <= plugin.getConfig().getInt("y-int"); y++) {

								for (int z = -plugin.getConfig().getInt("z-int"); z <= plugin.getConfig().getInt("z-int"); z++) {

									br = b.getRelative(x, y, z);

									if (!br.getType().equals(Material.DISPENSER)) {

										continue;

									}

									dispensers.add((Dispenser) br.getState());

								}
							}
						}

						if(dispensers.isEmpty()) {

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-dispensers")));

							return true;

						}

						int tntCount = 0;

						for (ItemStack i : p.getInventory().getContents()) {

							if(i == null) {
								
								continue;
								
							}
							
							if (i.getType().equals(Material.TNT)) {

								tntCount += i.getAmount();

							}
						}

						p.getInventory().remove(Material.TNT);

						int i = tntCount;
						while (i > 0) {

							for (Dispenser d : dispensers) {

								if (i <= 0) {

									break;

								}

								d.getInventory().addItem(new ItemStack(Material.TNT));
								i--;

							}
						}

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("filled-dispensers")));

					} else {

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-tnt")));

					}
				} else {

					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission")));

				}
			} else {
				
				s.sendMessage("Only players can execute this command");
				
			}
		}
		return true;
	}
}
