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

import java.sql.Connection;

public abstract class DatabaseHandler {

    /**
     * Returns the database context
     * @return the database context
     */
    public abstract DSLContext getContext();

    /**
     * Returns if the database is connected to
     * @return if the database is connected to
     */
    public abstract boolean connected();

    /**
     * Returns an open connection to the database
     * @return an open connection to the database
     */
    public abstract Connection open();

    /**
     * Closes the connection to the database
     * @return if the connection was closed
     */
    public abstract boolean close();
}
