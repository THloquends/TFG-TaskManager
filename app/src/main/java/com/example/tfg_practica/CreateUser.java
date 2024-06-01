package com.example.tfg_practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Empresa;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class CreateUser extends AppCompatActivity {
    private Empresa empresa;
    private User user;
    private int type;
    private Helper helper;
    private Dao<Empresa, Integer> daoEmpresa;
    private Dao<User, Integer> daoUser;
    private EditText name;
    private RadioButton worker;
    private RadioButton admin;
    private Button back;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin.isSelected()) {
                    type = 1;
                } else {
                    type = 0;
                }

                try {
                    user = new User(empresa, name.getText().toString(), type, "", "");
                    daoUser.create(user);

                    Intent intent = new Intent(CreateUser.this, UsersList.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void load() throws SQLException {
        name = findViewById(R.id.txtUserNameCreate);
        worker = findViewById(R.id.rbTrabajador);
        admin = findViewById(R.id.rbAdmin);
        create = findViewById(R.id.btnCreateUserMenu);
        back = findViewById(R.id.btnBackUserCreate);

        helper = new Helper(this, Config.BD_NAME, null, Config.BD_VERSION);

        if (helper != null){
            daoEmpresa = helper.getDaoEmpresas();
            empresa = daoEmpresa.queryForId(1);
            daoUser = helper.getDaoUsers();
        }
    }
}