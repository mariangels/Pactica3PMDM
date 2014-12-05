package com.izv.fragmentosorientacion;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ActividadDetalle extends Activity {

    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        s= getIntent().getStringExtra("texto");
        final FragmentoDetalle fDet= (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fDet);
        fDet.mostrar(null);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //cuando tenemos en vertical el fragmento 2 y le damos la vuelta y tenemos que cerrar fragmento 2
        //y abrir en landscape con el contenido anterior
        finish();
    }

}
