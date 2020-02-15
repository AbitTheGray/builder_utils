package net.graymadness.builder_utils;

import net.graymadness.builder_utils.command.BackCommand;
import net.graymadness.builder_utils.command.NightvisionCommand;
import net.graymadness.builder_utils.command.SpeedCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class BuilderPlugin extends JavaPlugin implements Listener
{

    @Override
    public void onEnable()
    {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(this, this);

        // /speed
        {
            SpeedCommand command = new SpeedCommand(this);
            pm.registerEvents(command, this);

            PluginCommand cmd = getCommand("speed");
            cmd.setExecutor(command);
            cmd.setTabCompleter(command);
        }

        // /nightvision
        {
            NightvisionCommand command = new NightvisionCommand(this);
            pm.registerEvents(command, this);

            PluginCommand cmd = getCommand("nightvision");
            cmd.setExecutor(command);
            cmd.setTabCompleter(command);
        }

        // /back
        {
            BackCommand command = new BackCommand(this);
            pm.registerEvents(command, this);

            PluginCommand cmd = getCommand("back");
            cmd.setExecutor(command);
            cmd.setTabCompleter(command);
        }
    }

    @Override
    public void onDisable()
    {

    }

    @NotNull
    public static List<String> filterOnly(@Nullable String part, @NotNull List<String> list)
    {
        if(part == null)
            return list;

        List<String> rtn = new ArrayList<>();
        for(String s : list)
            if(s.startsWith(part))
                rtn.add(s);
        return rtn;
    }
}
