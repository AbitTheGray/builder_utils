package net.graymadness.builder_utils.command;

import net.graymadness.builder_utils.BuilderPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedCommand implements Listener, CommandExecutor, TabCompleter
{
    @NotNull
    private BuilderPlugin plugin;

    public SpeedCommand(@NotNull BuilderPlugin plugin)
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

        int amplifier = 0;
        if(args.length > 0)
        {
            try
            {
                amplifier = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException ex)
            {
                amplifier = 0;
            }
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60 * 24, amplifier, false, false), true);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(args.length == 1)
        {
            List<String> values = new ArrayList<>();
            for(int i = 0; i <= 10; i++)
                values.add(i + "");

            return BuilderPlugin.filterOnly(args[0], values);
        }
        return new ArrayList<>();
    }
}
