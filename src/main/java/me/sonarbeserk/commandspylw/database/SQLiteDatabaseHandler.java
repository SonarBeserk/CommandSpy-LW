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

import org.jooq.DSLContext;
import org.jooq.SQLDialect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jooq.impl.DSL.using;

public class SQLiteDatabaseHandler extends DatabaseHandler {
    private final Logger log;
    private final String path, dbName;
    private Connection conn = null;

    public SQLiteDatabaseHandler(Logger log, String path, String dbname) {
        this.log = log;
        this.path = path;
        this.dbName = dbname;
    }

    @Override
    public DSLContext getContext() {
        if (connected()) {
            return using(conn, SQLDialect.SQLITE);
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
            Class.forName("org.sqlite.JDBC").newInstance();
            conn = DriverManager.getConnection("jdbc:sqlite:" + path + "/" + dbName + ".db");
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
