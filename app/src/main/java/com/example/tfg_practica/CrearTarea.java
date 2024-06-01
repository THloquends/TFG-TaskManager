package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.Tarea;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class CrearTarea extends AppCompatActivity {
    private Tarea tarea;
    private Proyecto proyecto;
    private User user;
    private ArrayList<User> users;
    private Helper helper;
    private Dao<Tarea, Integer> daoTareas;
    private Dao<Proyecto, Integer> daoProyectos;
    private Dao<User, Integer> daoUser;
    private EditText descrip;
    private Spinner listaUsers;
    private Button btnBack, btnCreate;
    private Bundle bundle;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = buscarUser((String)listaUsers.getSelectedItem());
                tarea = new Tarea(proyecto, user, descrip.getText().toString(), 0);
                try {
                    daoTareas.create(tarea);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(CrearTarea.this, DetallesProyecto.class);
                startActivity(intent);
            }
        });
    }

    private void load() throws SQLException {
        bundle = getIntent().getExtras();
        int id = bundle.getInt("proyecto");

        descrip = findViewById(R.id.txtDescripCT);
        listaUsers = findViewById(R.id.TareaUseList);
        btnCreate = findViewById(R.id.btnCrearTarea);
        btnBack = findViewById(R.id.btnBackCT);

        listaNombres = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        helper = new Helper(this, Config.BD_NAME, null, Config.BD_VERSION);

        if (helper != null){
            daoProyectos = helper.getDaoProyectos();
            daoUser = helper.getDaoUsers();
            daoTareas = helper.getDaoTareas();

            proyecto = daoProyectos.queryForId(id);

            users = (ArrayList<User>) daoUser.queryForAll();
            crearNombres();
            listaUsers.setAdapter(adapter);
        }
    }

    private void crearNombres() {
        for (User u: users) {
            listaNombres.add(u.getName());
        }
    }

    private User buscarUser(String name) {
        for (User u: users) {
            if (u.getName().equals(name)){
                return u;
            }
        }
        return new User();
    }
}