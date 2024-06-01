package com.example.tfg_practica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_practica.R;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserVH> {
    private Context context;
    private List<User> users;
    private int resource;

    public UsersAdapter(Context context, List<User> users, int resource) {
        this.context = context;
        this.users = users;
        this.resource = resource;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(context).inflate(resource, null);

        userView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        try {
            return new UserVH(userView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        User user = users.get(position);
        String tipo;
        if (user.getType() == 0){
            tipo = "Trabajador";
        } else {
            tipo = "Administrador";
        }
        holder.name.setText(user.getName());
        holder.type.setText(tipo);

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (user.getId() == 1){
            holder.btnDelete.setEnabled(false);
            holder.btnDelete.setVisibility(View.INVISIBLE);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User aux = users.get(holder.getAdapterPosition());
                users.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                try {
                    holder.daoUsers.delete(aux);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class UserVH extends RecyclerView.ViewHolder{
        TextView name, type;
        ImageButton btnView, btnDelete;
        Helper helper;
        Dao<User, Integer> daoUsers;
        public UserVH(@NonNull View itemView) throws SQLException {
            super(itemView);
            name = itemView.findViewById(R.id.lbCardUserName);
            type = itemView.findViewById(R.id.lbCardUserType);
            btnView = itemView.findViewById(R.id.btnViewUser);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);

            helper = new Helper(context, Config.BD_NAME, null ,Config.BD_VERSION);
            if (helper != null){
                daoUsers = helper.getDaoUsers();
            }
        }
    }
}
