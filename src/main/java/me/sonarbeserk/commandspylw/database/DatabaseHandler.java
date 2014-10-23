package me.sonarbeserk.commandspylw.database;

import org.jooq.DSLContext;

import java.sql.Connection;

public abstract class DatabaseHandler {
    public abstract DSLContext getContext();

    public abstract boolean connected();

    public abstract Connection open();

    public abstract boolean close();
}
