package com.example.tfg_practica.objetos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Empresa")
public class Empresa {
    @DatabaseField(columnName = "IdEmpresa", generatedId = true)
    int id;
    @DatabaseField(columnName = "Nombre", canBeNull = false)
    String name;
    @DatabaseField(columnName = "Password", canBeNull = false)
    String passw;

    public Empresa() {
    }

    public Empresa(String name, String passw) {
        this.name = name;
        this.passw = passw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passw='" + passw + '\'' +
                '}';
    }
}
