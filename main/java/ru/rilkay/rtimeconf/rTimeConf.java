package ru.rilkay.rtimeconf;

import org.bukkit.plugin.java.JavaPlugin;

public final class rTimeConf extends JavaPlugin {

    private static rTimeConf instance;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();

        EnvCommand envCommand = new EnvCommand();
        if (getCommand("ptime") != null) getCommand("ptime").setExecutor(envCommand);
        if (getCommand("pweather") != null) getCommand("pweather").setExecutor(envCommand);
        if (getCommand("pt") != null) getCommand("pt").setExecutor(envCommand);

        if (getCommand("ptime") != null) getCommand("ptime").setTabCompleter(envCommand);
        if (getCommand("pweather") != null) getCommand("pweather").setTabCompleter(envCommand);
        if (getCommand("pt") != null) getCommand("pt").setTabCompleter(envCommand);

        getLogger().info("rTimeConf has been started successfully!");
    }

    public static rTimeConf getInstance() {
        return instance;
    }
}
