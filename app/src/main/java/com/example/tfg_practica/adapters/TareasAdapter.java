package com.example.tfg_practica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_practica.R;
import com.example.tfg_practica.config.Config;
import com.example.tfg_practica.helpers.Helper;
import com.example.tfg_practica.objetos.Proyecto;
import com.example.tfg_practica.objetos.Tarea;
import com.example.tfg_practica.objetos.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaVH>{
    private Context context;
    private List<Tarea> tareas;
    private int resource;

    public TareasAdapter(Context context, List<Tarea> tareas, int resource) {
        this.context = context;
        this.tareas = tareas;
        this.resource = resource;
    }

    @NonNull
    @Override
    public TareaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tareaView = LayoutInflater.from(context).inflate(resource, null);

        tareaView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        try {
            return new TareaVH(tareaView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TareaVH holder, int position) {
        Tarea tarea = tareas.get(position);
        try {
            holder.user = holder.daoUsers.queryForId(tarea.getIdusuario().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        holder.descrip.setText(tarea.getDescrip());
        holder.empleado.setText(holder.user.getName());

        if (tarea.getEstado() == 0){
            holder.completado.setChecked(false);
        } else {
            holder.completado.setChecked(true);
        }

        holder.completado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tarea.setEstado(1);
                    try {
                        holder.daoTareas.update(tarea);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    tarea.setEstado(0);
                    try {
                        holder.daoTareas.update(tarea);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public class TareaVH extends RecyclerView.ViewHolder{
        CheckBox completado;
        TextView descrip, empleado;
        Helper helper;
        Dao<Tarea, Integer> daoTareas;
        Dao<User, Integer> daoUsers;
        User user;
        public TareaVH(@NonNull View itemView) throws SQLException {
            super(itemView);
            completado = itemView.findViewById(R.id.chCompletado);
            descrip = itemView.findViewById(R.id.txtCardDescripTarea);
            empleado = itemView.findViewById(R.id.txtCardEmpleadoTarea);

            helper = new Helper(context, Config.BD_NAME, null ,Config.BD_VERSION);
            if (helper != null){
                daoTareas = helper.getDaoTareas();
                daoUsers = helper.getDaoUsers();
            }
        }
    }
}
