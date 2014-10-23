/**
 * *********************************************************************************************************************
 * CommandSpyLW - Lightweight command tracking and logging
 * =====================================================================================================================
 * Copyright (C) 2012-2014 by SonarBeserk
 * https://github.com/SonarBeserk/CommandSpy-LW
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

package me.sonarbeserk.commandspylw;

import me.sonarbeserk.beserkcore.plugin.UpdatingJavaPlugin;
import me.sonarbeserk.commandspylw.database.DatabaseHandler;
import me.sonarbeserk.commandspylw.database.MysqlDatabaseHandler;
import me.sonarbeserk.commandspylw.database.SQLiteDatabaseHandler;

public class CommandSpyLW extends UpdatingJavaPlugin {
    public static String databaseName = null;
    public static String prefix = null;

    public static boolean useMariaDBDialect = false;

    private DatabaseHandler databaseHandler = null;

    @Override
    public void onEnable() {
        super.onEnable();

        databaseName = getConfig().getString("settings.database.name");
        prefix = getConfig().getString("settings.database.prefix");
        useMariaDBDialect = getConfig().getBoolean("settings.database.mysql.mariadbDialect");

        startDatabase();
    }

    @Override
    public int getProjectID() {
        return 0; // Replace at distribution
    }

    @Override
    public boolean shouldSaveData() {
        return false;
    }

    @Override
    public boolean registerPremadeMainCMD() {
        return true;
    }

    @Override
    public String getPermissionPrefix() {
        return getConfig().getString("settings.permissionPrefix");
    }

    private void startDatabase() {
        if(getConfig().getString("settings.databaseType").equalsIgnoreCase("sqlite")) {
            databaseHandler = new SQLiteDatabaseHandler(getLogger(), getConfig().getString("settings.database.sqlite.path").replace("{plugindata}", getDataFolder().getPath()), getConfig().getString("settings.database.name"));
        } else if(getConfig().getString("settings.databaseType").equalsIgnoreCase("mysql")) {
            useMariaDBDialect = getConfig().getBoolean("settings.database.mysql.mariadbDialect");
            databaseHandler = new MysqlDatabaseHandler(getLogger(), getConfig().getString("settings.database.mysql.host"), getConfig().getString("settings.database.mysql.port"), getConfig().getString("settings.database.mysql.database"), getConfig().getString("settings.database.mysql.username"), getConfig().getString("settings.database.mysql.password"));

        }
    }

    /**
     * Returns the database handler
     * @return the database handler
     */
    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
