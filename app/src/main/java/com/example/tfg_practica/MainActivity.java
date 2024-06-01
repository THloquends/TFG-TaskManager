package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tfg_practica.adapters.ProyectosAdapter;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Empresa;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Empresa empresa;
    private ArrayList<Empresa> contarEmpresas;
    private User user;
    private ArrayList<Proyecto> proyectos;
    private Helper helper;
    private Dao<Empresa, Integer> daoEmpresa;
    private Dao<User, Integer> daoUser;
    private Dao<Proyecto, Integer> daoProyecto;
    private ProyectosAdapter proyectosAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Intent intent;
    private Button btnUser;
    private Button btnProyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //this.deleteDatabase(Config.BD_NAME);
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, UsersList.class);
                startActivity(intent);
            }
        });

        btnProyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CreateProject.class);
                startActivity(intent);
            }
        });
    }

    private void load() throws SQLException {
        btnUser = findViewById(R.id.btnIrUsuariosMain);
        btnProyectos = findViewById(R.id.btnIrCrearProyectos);
        recyclerView = findViewById(R.id.contenedor);

        empresa = new Empresa("EMPRESA","-a1233456");

        proyectos = new ArrayList<>();

        helper = new Helper(this, Config.BD_NAME, null ,Config.BD_VERSION);

        if (helper != null){
            daoEmpresa = helper.getDaoEmpresas();
            daoUser = helper.getDaoUsers();
            daoProyecto = helper.getDaoProyectos();

            contarEmpresas = (ArrayList<Empresa>) daoEmpresa.queryForAll();
            if (contarEmpresas.size() == 0){
                daoEmpresa.create(empresa);
            }

            if (((ArrayList<User>) daoUser.queryForAll()).size() == 0){
                user = new User(empresa, "admin", 1, "", "");
                daoUser.create(user);
            }
            if (proyectos.isEmpty()){
                proyectos = (ArrayList<Proyecto>) daoProyecto.queryForAll();
            }
        }

        proyectosAdapter = new ProyectosAdapter(MainActivity.this, proyectos, R.layout.proyectos_view_holder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(proyectosAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}