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

package me.sonarbeserk.commandspylw.database;

import me.sonarbeserk.commandspylw.CommandSpyLW;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jooq.impl.DSL.using;

public class MysqlDatabaseHandler extends DatabaseHandler {
    private final Logger log;
    private final String host, port, database, username, password;
    private Connection conn = null;

    public MysqlDatabaseHandler(Logger log, String host, String port, String database, String username, String password) {
        this.log = log;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public DSLContext getContext() {
        if (connected()) {
            if (CommandSpyLW.useMariaDBDialect) {
                return using(conn, SQLDialect.MARIADB);
            } else {
                return using(conn, SQLDialect.MYSQL);
            }
        }

        return null;
    }

    @Override
    public boolean connected() {
        try {
            if(conn == null) {
                return false;
            }

            return !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Connection open() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        } catch (Exception e) {
            log.log(Level.WARNING, "Failed to connect to database: " + e.getMessage());
        }

        return conn;
    }

    @Override
    public boolean close() {
        if (connected()) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed to close connection: " + e.getMessage());
                return false;
            }
        }

        return true;
    }
}
