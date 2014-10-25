/**
 * *********************************************************************************************************************
 * BeserkCore - Provides the core for plugins made by SonarBeserk
 * =====================================================================================================================
 * Copyright (C) 2014 by SonarBeserk
 * https://github.com/SonarBeserk/BeserkCore
 * *********************************************************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * *********************************************************************************************************************
 * Please refer to LICENSE for the full license. If it is not there, see <http://www.gnu.org/licenses/>.
 * *********************************************************************************************************************
 */

package me.sonarbeserk.commandspylw.commands;

import me.sonarbeserk.commandspylw.CommandSpyLW;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCmd implements CommandExecutor {
    private CommandSpyLW plugin = null;

    public SpyCmd(CommandSpyLW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            helpSubCommand(sender);
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("help")) {
                helpSubCommand(sender);
                return true;
            }

            if(args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("e")) {
                enableSubCommand(sender);
                return true;
            }

            if(args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("d")) {
                disableSubCommand(sender);
                return true;
            }
        }

        helpSubCommand(sender);
        return true;
    }

    private boolean permissionCheck(CommandSender sender, String permission, boolean autoMessage) {
        if (!sender.hasPermission(permission)) {
            if (autoMessage) {
                if (sender instanceof Player) {
                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("noPermission"));
                    return false;
                } else {
                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("noPermission"));
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void helpSubCommand(CommandSender sender) {
        if (sender instanceof Player) {
            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("usageSpy").replace("{name}", plugin.getDescription().getName()));
            return;
        } else {
            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("usageSpy").replace("{name}", plugin.getDescription().getName()));
            return;
        }
    }

    private void enableSubCommand(CommandSender sender) {
        if(!(sender instanceof Player)) {
            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("commandPlayerRequired"));
            return;
        }

        if(!permissionCheck(sender, plugin.getPermissionPrefix() + ".commands.spy.enable", true)) {
            return;
        }

        if(plugin.isSpying(((Player) sender).getUniqueId())) {
            plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("alreadySpying"));
            return;
        }

        plugin.enableSpying(((Player) sender).getUniqueId());
        plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("spyingEnabled"));
    }

    private void disableSubCommand(CommandSender sender) {
        if(!(sender instanceof Player)) {
            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("commandPlayerRequired"));
            return;
        }

        if(!permissionCheck(sender, plugin.getPermissionPrefix() + ".commands.spy.disable", true)) {
            return;
        }

        if(!plugin.isSpying(((Player) sender).getUniqueId())) {
            plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("notSpying"));
            return;
        }

        plugin.disableSpying(((Player) sender).getUniqueId());
        plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("spyingDisabled"));
    }
}
