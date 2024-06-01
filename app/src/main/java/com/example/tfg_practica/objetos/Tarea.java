package com.example.tfg_practica.objetos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tarea")
public class Tarea {
    @DatabaseField(columnName = "IdTarea", generatedId = true)
    int id;
    @DatabaseField(columnName = "IdProyecto", foreign = true)
    Proyecto idproyecto;
    @DatabaseField(columnName = "IdUsuario", foreign = true)
    User idusuario;
    @DatabaseField(columnName = "Descrip", canBeNull = false)
    String descrip;
    @DatabaseField(columnName = "Estado", canBeNull = false)
    int estado;

    public Tarea() {
    }

    public Tarea(Proyecto idproyecto, User idusuario, String descrip, int estado) {
        this.idproyecto = idproyecto;
        this.idusuario = idusuario;
        this.descrip = descrip;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proyecto getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Proyecto idproyecto) {
        this.idproyecto = idproyecto;
    }

    public User getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(User idusuario) {
        this.idusuario = idusuario;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
