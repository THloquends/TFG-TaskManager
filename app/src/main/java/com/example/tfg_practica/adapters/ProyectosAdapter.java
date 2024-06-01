package com.example.tfg_practica.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_practica.DetallesProyecto;
import com.example.tfg_practica.R;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Proyecto;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class ProyectosAdapter extends RecyclerView.Adapter<ProyectosAdapter.ProyectoVH> {
    private Context context;
    private List<Proyecto> proyectos;
    private int resource;

    public ProyectosAdapter(Context context, List<Proyecto> proyectos, int resource) {
        this.context = context;
        this.proyectos = proyectos;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ProyectoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View proyectView = LayoutInflater.from(context).inflate(resource, null);

        proyectView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
                ));


        try {
            return new ProyectoVH(proyectView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ProyectoVH holder, int position) {
        Proyecto proyecto = proyectos.get(position);
        holder.name.setText(proyecto.getName());
        holder.descrip.setText(proyecto.getDescrip());

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetallesProyecto.class);
                Bundle bundle = new Bundle();
                bundle.putInt("proyecto",proyecto.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Proyecto aux = proyectos.get(holder.getAdapterPosition());
                proyectos.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                try {
                    holder.daoProyectos.delete(aux);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }

    public class ProyectoVH extends RecyclerView.ViewHolder{
        TextView name, descrip;
        ImageButton btnView, btnDelete;
        Helper helper;
        Dao<Proyecto, Integer> daoProyectos;
        public ProyectoVH(@NonNull View itemView) throws SQLException {
            super(itemView);
            name = itemView.findViewById(R.id.lbCardName);
            descrip = itemView.findViewById(R.id.lbCardDescrip);
            btnView = itemView.findViewById(R.id.btnViewProyect);
            btnDelete = itemView.findViewById(R.id.btnDeleteProyect);

            helper = new Helper(context, Config.BD_NAME, null ,Config.BD_VERSION);
            if (helper != null){
                daoProyectos = helper.getDaoProyectos();
            }
        }
    }
}
