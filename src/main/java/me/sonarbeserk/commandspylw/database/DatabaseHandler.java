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
    public abstract DSLContext getContext();

    public abstract boolean connected();

    public abstract Connection open();

    public abstract boolean close();
}
