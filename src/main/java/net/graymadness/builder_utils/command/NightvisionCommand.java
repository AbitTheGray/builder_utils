package net.graymadness.builder_utils.command;

import net.graymadness.builder_utils.BuilderPlugin;
import net.graymadness.builder_utils.event.ServerStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NightvisionCommand implements Listener, CommandExecutor, TabCompleter
{
    @NotNull
    private BuilderPlugin plugin;

    public NightvisionCommand(@NotNull BuilderPlugin plugin)
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

        if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        else
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60 * 60 * 24, 0, false, false));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        return new ArrayList<>();
    }

    private void onPlayerQuit(@NotNull Player player)
    {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event)
    {
        onPlayerQuit(event.getPlayer());
    }

    @EventHandler
    private void onDisable(ServerStopEvent event)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            onPlayerQuit(player);
    }
}
