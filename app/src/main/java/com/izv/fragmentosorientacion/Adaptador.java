package com.izv.fragmentosorientacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Vivienda> {

    private ArrayList<Vivienda> lista;
    private int recurso;

    private static LayoutInflater i;

    public static class ViewHolder{
        public TextView tv1;
    }

    public Adaptador(Context context, int resource, ArrayList<Vivienda> viviendas) {
        super(context, resource, viviendas);

        this.lista = viviendas;
        this.recurso = resource;

        this.i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //CREAMOS INFLADOR
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tvTexto);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        String muestra=lista.get(position).mostrar();
        vh.tv1.setText(muestra);

        return convertView;
    }

}
