package com.example.tfg_practica.objetos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "Usuario")
public class User implements Serializable {
    @DatabaseField(columnName = "IdUsuario", generatedId = true)
    private int id;
    @DatabaseField(columnName = "IdEmpresa", foreign = true)
    private Empresa idempresa;
    @DatabaseField(columnName = "Nombre", canBeNull = false)
    private String name;
    @DatabaseField(columnName = "Tipo", canBeNull = false)
    private int type;
    @DatabaseField(columnName = "Imagen")
    private String img;
    @DatabaseField(columnName = "Oficio")
    private String oficio;

    public User() {
    }

    public User(Empresa idempresa, String name, int type, String img, String oficio) {
        this.idempresa = idempresa;
        this.name = name;
        this.type = type;
        this.img = img;
        this.oficio = oficio;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }
}
