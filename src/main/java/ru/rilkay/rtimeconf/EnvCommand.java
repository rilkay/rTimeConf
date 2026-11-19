package ru.rilkay.rtimeconf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnvCommand implements CommandExecutor, TabCompleter {

    private final MiniMessage mm = MiniMessage.miniMessage();

    private String getPrefix() {
        return rTimeConf.getInstance().getConfig().getString("prefix", "");
    }

    private void sendMsg(Player player, String configPath) {
        String msg = rTimeConf.getInstance().getConfig().getString("messages." + configPath, "");
        if (!msg.isEmpty()) {
            msg = msg.replace("{prefix}", getPrefix());
            player.sendMessage(mm.deserialize(msg));
        }
    }

    private void sendMsgWithValue(Player player, String configPath, String value) {
        String msg = rTimeConf.getInstance().getConfig().getString("messages." + configPath, "");
        if (!msg.isEmpty()) {
            msg = msg.replace("{prefix}", getPrefix()).replace("{value}", value);
            player.sendMessage(mm.deserialize(msg));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String cmdName = command.getName().toLowerCase();

        if (cmdName.equals("pt")) {
            if (args.length == 0) {
                if (sender instanceof Player p) sendMsg(p, "invalid-args-admin");
                else sender.sendMessage(getPrefix() + "§cUsage: /pt <reload|world>");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("rtimeconf.admin")) {
                    if (sender instanceof Player p) sendMsg(p, "no-permission");
                    else sender.sendMessage(getPrefix() + "§cYou don't have permission!");
                    return true;
                }
                rTimeConf.getInstance().reloadConfig();
                if (sender instanceof Player p) sendMsg(p, "reload-success");
                else sender.sendMessage(getPrefix() + "§aConfig reloaded successfully!");
                return true;
            }

            if (args[0].equalsIgnoreCase("world")) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("This command can only be used in-game!");
                    return true;
                }
                if (!player.hasPermission("rtimeconf.use")) {
                    sendMsg(player, "no-permission");
                    return true;
                }
                player.resetPlayerTime();
                player.resetPlayerWeather();
                sendMsg(player, "world-enabled");
                return true;
            }
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used in-game!");
            return true;
        }

        if (!player.hasPermission("rtimeconf.use")) {
            sendMsg(player, "no-permission");
            return true;
        }

        if (cmdName.equals("ptime")) {
            if (args.length == 0) {
                sendMsg(player, "invalid-args-time");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "day" -> {
                    player.setPlayerTime(1000, false);
                    sendMsgWithValue(player, "time-changed", "day");
                }
                case "night" -> {
                    player.setPlayerTime(13000, false);
                    sendMsgWithValue(player, "time-changed", "night");
                }

                case "noon" -> {
                    player.setPlayerTime(6000, false);
                    sendMsgWithValue(player,  "time-changed", "noon");
                }

                case "midnight" -> {
                    player.setPlayerTime(18000, false);
                    sendMsgWithValue(player,  "time-changed", "midnight");
                }

                case "reset" -> {
                    player.resetPlayerTime();
                    sendMsg(player, "time-reset");
                }
                default -> {
                    try {
                        long time = Long.parseLong(args[0]);
                        player.setPlayerTime(time, false);
                        sendMsgWithValue(player, "time-changed", String.valueOf(time));
                    } catch (NumberFormatException e) {
                        sendMsg(player, "invalid-args-time");
                    }
                }
            }
            return true;
        }

        if (cmdName.equals("pweather")) {
            if (args.length == 0) {
                sendMsg(player, "invalid-args-weather");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "sun", "clear" -> {
                    player.setPlayerWeather(WeatherType.CLEAR);
                    sendMsgWithValue(player, "weather-changed", args[0].toLowerCase());
                }
                case "rain", "storm" -> {
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    sendMsgWithValue(player, "weather-changed", args[0].toLowerCase());
                }
                case "reset" -> {
                    player.resetPlayerWeather();
                    sendMsg(player, "weather-reset");
                }
                default -> sendMsg(player, "invalid-args-weather");
            }
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        
        // Интересует только первый аргумент каждой команды
        if (args.length == 1) {
            String cmdName = command.getName().toLowerCase();

            if (cmdName.equals("pt")) {
                
                if (sender.hasPermission("rtimeconf.admin")) {
                    completions.add("reload");
                }
                
                if (sender.hasPermission("rtimeconf.use")) {
                    completions.add("world");
                }
            } 
            else if (cmdName.equals("ptime") && sender.hasPermission("rtimeconf.use")) {
                completions.add("day");
                completions.add("noon");
                completions.add("night");
                completions.add("midnight");
                completions.add("reset");
            } 
            else if (cmdName.equals("pweather") && sender.hasPermission("rtimeconf.use")) {
                completions.add("sun");
                completions.add("clear");
                completions.add("rain");
                completions.add("storm");
                completions.add("reset");
            }

            
            String input = args[0].toLowerCase();
            return completions.stream()
                    .filter(s -> s.startsWith(input))
                    .toList();
        }

       
        return new ArrayList<>();
    }
}
