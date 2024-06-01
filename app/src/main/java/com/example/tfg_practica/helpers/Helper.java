package com.example.tfg_practica.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.tfg_practica.objetos.Empresa;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.Tarea;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Helper extends OrmLiteSqliteOpenHelper {
    private Dao<Empresa, Integer> daoEmpresas;
    private Dao<User, Integer> daoUsers;
    private Dao<Proyecto, Integer> daoProyectos;
    private Dao<Tarea, Integer> daoTareas;
    public Helper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Empresa.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Proyecto.class);
            TableUtils.createTable(connectionSource, Tarea.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<User, Integer> getDaoUsers() throws SQLException {
        if (daoUsers == null){
            daoUsers = getDao(User.class);
        }
        return daoUsers;
    }

    public Dao<Empresa, Integer> getDaoEmpresas() throws SQLException {
        if (daoEmpresas == null){
            daoEmpresas = getDao(Empresa.class);
        }
        return daoEmpresas;
    }

    public Dao<Proyecto, Integer> getDaoProyectos() throws SQLException {
        if (daoProyectos == null){
            daoProyectos = getDao(Proyecto.class);
        }
        return daoProyectos;
    }

    public Dao<Tarea, Integer> getDaoTareas() throws SQLException {
        if (daoTareas == null){
            daoTareas = getDao(Tarea.class);
        }
        return daoTareas;
    }
}
