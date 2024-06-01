package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tfg_practica.adapters.ProyectosAdapter;
import com.example.tfg_practica.adapters.UsersAdapter;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {
    private User user;
    private List<User> users;
    private Button btnProjects;
    private Button btnCreate;
    private UsersAdapter usersAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Helper helper;
    private Dao<User, Integer> daoUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersList.this, CreateUser.class);
                startActivity(intent);
            }
        });
    }

    private void load() throws SQLException {
        users = new ArrayList<>();
        btnProjects = findViewById(R.id.btnIrProyectos);
        btnCreate = findViewById(R.id.btnCreateUser);
        recyclerView = findViewById(R.id.userContainer);

        helper = new Helper(this, Config.BD_NAME, null, Config.BD_VERSION);

        if (helper != null){
            daoUsers = helper.getDaoUsers();
            if (users.isEmpty()){
                users = daoUsers.queryForAll();
            }
        }

        usersAdapter = new UsersAdapter(UsersList.this, users, R.layout.users_view_holder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}