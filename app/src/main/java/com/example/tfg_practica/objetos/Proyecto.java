package com.example.tfg_practica.objetos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Proyecto")
public class Proyecto {
    @DatabaseField(columnName = "IdProyecto", generatedId = true)
    int id;
    @DatabaseField(columnName = "IdEmpresa", foreign = true)
    Empresa idempresa;
    @DatabaseField(columnName = "IdLider", foreign = true)
    User idlider;
    @DatabaseField(columnName = "Nombre", canBeNull = false)
    String name;
    @DatabaseField(columnName = "Descrip", canBeNull = false)
    String descrip;

    public Proyecto() {
    }

    public Proyecto(Empresa idempresa, User idlider, String name, String descrip) {
        this.idempresa = idempresa;
        this.idlider = idlider;
        this.name = name;
        this.descrip = descrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empresa getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresa idempresa) {
        this.idempresa = idempresa;
    }

    public User getIdlider() {
        return idlider;
    }

    public void setIdlider(User idlider) {
        this.idlider = idlider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
