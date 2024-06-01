package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfg_practica.adapters.ProyectosAdapter;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Empresa;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateProject extends AppCompatActivity {
    private Empresa empresa;
    private User user;
    private Proyecto proyecto;
    private Helper helper;
    private Dao<Empresa, Integer> daoEmpresa;
    private Dao<User, Integer> daoUser;
    private Dao<Proyecto, Integer> daoProyecto;
    private ArrayAdapter<String> adapter;
    private ProyectosAdapter proyectosAdapter;
    private List<User> userList;
    private ArrayList<String> listaNombres;
    private EditText name;
    private EditText descrip;
    private Spinner listUser;
    private Button btnCreate;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        try {
            load();

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user = buscarUser((String)listUser.getSelectedItem());
                    proyecto = new Proyecto(empresa, user, name.getText().toString(), descrip.getText().toString());
                    try {
                        daoProyecto.create(proyecto);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Intent intent = new Intent(CreateProject.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() throws SQLException {
        name = findViewById(R.id.txtNombreCP);
        descrip = findViewById(R.id.txtDescripCP);
        listUser = findViewById(R.id.listUsersCP);
        btnCreate = findViewById(R.id.btnCrearCP);
        btnBack = findViewById(R.id.btnAtrasCP);

        listaNombres = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        helper = new Helper(this, Config.BD_NAME, null, Config.BD_VERSION);

        if (helper != null){
            daoEmpresa = helper.getDaoEmpresas();
            daoUser = helper.getDaoUsers();
            daoProyecto = helper.getDaoProyectos();

            empresa = daoEmpresa.queryForFirst();

            if (daoUser != null) {
                userList = daoUser.queryForAll();
                crearNombres();
                listUser.setAdapter(adapter);
            }
        }
    }

    private void crearNombres() {
        for (User u: userList) {
            listaNombres.add(u.getName());
        }
    }

    private User buscarUser(String name) {
        for (User u: userList) {
            if (u.getName().equals(name)){
                return u;
            }
        }
        return new User();
    }
}