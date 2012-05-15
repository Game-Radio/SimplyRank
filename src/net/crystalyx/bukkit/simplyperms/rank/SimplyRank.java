package net.crystalyx.bukkit.simplyperms.rank;

import java.util.Arrays;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimplyRank extends JavaPlugin implements CommandExecutor {

	private SimplyAPI api;

	public void onEnable() {
		api = ((SimplyPlugin) getServer().getPluginManager().getPlugin("SimplyPerms")).getAPI();
		getCommand("setrank").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] split) {
		try {
			if (sender.hasPermission("permissions.rank." + split[1])) {
				String player = split[0].toLowerCase();
				Player target = getServer().getPlayer(split[0]);
				String PlayerName = sender.getName();
				String carget = getServer().getPlayer(split[0]).getDisplayName();
				String[] groups = split[1].split(",");
				api.removePlayerGroups(player);
				for (String group : Arrays.asList(groups)) {
					api.addPlayerGroup(player, group);
				}
				sender.sendMessage(ChatColor.GREEN + "You've changed " + carget + "'s rank to " + split[1] + ".");
				target.sendMessage(ChatColor.AQUA + getServer().getPlayer(PlayerName).getDisplayName() + " changed your rank to " + split[1] + "!");
				getLogger().info(getServer().getPlayer(PlayerName).getDisplayName() + " changed " + carget + "'s rank to " + split[1] + ".");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this!");
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			sender.sendMessage(ChatColor.RED + "Wrong syntax! Usage: /rank [Player] [Rank]");
			return true;
		} catch (NullPointerException e) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.RED + "That player is not online!");
			}
			return true;
		}
	}

}
