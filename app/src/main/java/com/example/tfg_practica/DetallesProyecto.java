package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_practica.adapters.ProyectosAdapter;
import com.example.tfg_practica.adapters.TareasAdapter;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.Tarea;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class DetallesProyecto extends AppCompatActivity {
    private Proyecto proyecto;
    private User user;
    private Bundle bundle;
    private int id;
    private TextView nombre, lider, descrip;
    private RecyclerView recyclerView;
    private Helper helper;
    private Dao<Proyecto, Integer> daoProyectos;
    private Dao<User, Integer> daoUser;
    private Dao<Tarea, Integer> daoTareas;
    private TareasAdapter tareasAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Tarea> tareas;
    private ArrayList<Tarea> tareasFiltrado;
    private Button btnBack, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_proyecto);

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
                Intent intent = new Intent(DetallesProyecto.this, CrearTarea.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("proyecto", proyecto.getId());
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    private void load() throws SQLException {
        recyclerView = findViewById(R.id.TareaList);
        tareas = new ArrayList<>();
        tareasFiltrado = new ArrayList<>();
        bundle = getIntent().getExtras();

        if (bundle != null){
            id = bundle.getInt("proyecto");
        }

        helper = new Helper(this, Config.BD_NAME, null, Config.BD_VERSION);

        if (helper != null){
            daoProyectos = helper.getDaoProyectos();
            proyecto = daoProyectos.queryForId(id);

            daoUser = helper.getDaoUsers();
            user = daoUser.queryForId(proyecto.getIdlider().getId());

            daoTareas = helper.getDaoTareas();
            tareas = (ArrayList<Tarea>) daoTareas.queryForAll();
            filtrarTareas();
        }

        nombre = findViewById(R.id.lbTituloDP);
        lider = findViewById(R.id.lbLiderDP);
        descrip = findViewById(R.id.lbDescripDP);

        nombre.setText(proyecto.getName());
        lider.setText("Líder: " + user.getName());
        descrip.setText("Descripción: " + proyecto.getDescrip());

        btnBack = findViewById(R.id.btnBackDP);
        btnCreate = findViewById(R.id.btnIrCrearTarea);

        tareasAdapter = new TareasAdapter(DetallesProyecto.this, tareasFiltrado, R.layout.tarea_view_holder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(tareasAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void filtrarTareas() {
        for (Tarea t: tareas) {
            if (t.getIdproyecto().getId() == proyecto.getId()){
                tareasFiltrado.add(t);
            }
        }
    }
}