package net.graymadness.builder_utils.command;

import net.graymadness.builder_utils.BuilderPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackCommand implements Listener, CommandExecutor, TabCompleter
{
    @NotNull
    private BuilderPlugin plugin;

    public BackCommand(@NotNull BuilderPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "This command can only be used by player");
            return true;
        }
        Player player = (Player) sender;

        Location loc = lastLocations.getOrDefault(player, null);
        if(loc == null)
        {
            sender.sendMessage(ChatColor.RED + "You have no place to return to");
            return true;
        }

        player.teleport(loc);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        return new ArrayList<>();
    }

    @NotNull
    private final Map<Player, Location> lastLocations = new HashMap<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onTeleport(PlayerTeleportEvent event)
    {
        lastLocations.put(event.getPlayer(), event.getFrom().clone());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onQuit(PlayerQuitEvent event)
    {
        lastLocations.remove(event.getPlayer());
    }
}
