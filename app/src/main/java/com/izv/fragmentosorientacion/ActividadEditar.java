package com.izv.fragmentosorientacion;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


public class ActividadEditar extends Activity {

    private Vivienda v;
    private int i=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Bundle b = getIntent().getExtras();
        if(b!=null) {
            i=b.getInt("index");//si i es -1 no hay que editar
            if(i!=-1){
                v=(Vivienda)b.getSerializable("vivienda");
                mostrarVivienda();
            }
        }else{
            tostada("nulo");
        }
    }

    public void mostrarVivienda(){
        ((EditText) findViewById(R.id.eLocalidad)).setText(v.getLocalidad());
        ((EditText) findViewById(R.id.eCalle)).setText(v.getCalle());
        ((EditText) findViewById(R.id.eNumero)).setText(v.getNumero());
        ((EditText) findViewById(R.id.eTipo)).setText(v.getTipo());
        ((EditText) findViewById(R.id.ePrecio)).setText(v.getPrecio());
    }

    public void guardar(View view) {
        String localidad = ((EditText) findViewById(R.id.eLocalidad)).getText().toString();
        String calle = ((EditText) findViewById(R.id.eCalle)).getText().toString();
        String numero = ((EditText) findViewById(R.id.eNumero)).getText().toString();
        String tipo = ((EditText) findViewById(R.id.eTipo)).getText().toString();
        String precio = ((EditText) findViewById(R.id.ePrecio)).getText().toString();

        Vivienda v = new Vivienda(0, localidad, calle, numero, tipo, precio, null);
        //mandar palabras al Diccionario de vuelta
        Intent data = new Intent();
        data.putExtra("vivienda", v);//lo manda vacio
        data.putExtra("i",i);
        //cerrar editar
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void cancelar(View view){
        //QUE  NO  GUARDAS
        Intent data = new Intent();
        setResult(Activity.RESULT_OK, data);
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle savingInstanceState) {
        super.onSaveInstanceState(savingInstanceState);
        savingInstanceState.putString("cadena", "contenido");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String valor = savedInstanceState.getString("cadena");
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

