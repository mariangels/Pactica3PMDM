package com.izv.fragmentosorientacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActividadLista extends Activity{

    private ArrayList<Vivienda> va;
    private Adaptador ad;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        va=new ArrayList<Vivienda>();

        /*
        va.add(new Vivienda(1,"Las Gabias", "Chorrillo", "18", "casa", "18000", null));
        va.add(new Vivienda(2,"Armilla", "FLores", "21", "casa", "24000", null));
        va.add(new Vivienda(3,"Cullar", "Mariposas", "3", "piso", "17000", null));
        */

        leerxml();
        lv = (ListView)findViewById(R.id.listView);
        ad=new Adaptador(this,R.layout.elemento,va);
        lv.setAdapter(ad);

        //para el long click
        registerForContextMenu(lv);

        //Vamos a ver en que modo estamos, apaisado, o vertical.
        final FragmentoDetalle fDet= (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fDet);

        final boolean horizontal=fDet!=null && fDet.isInLayout();
        /*
        int v=getResources().getConfiguration().orientation;
        switch(v){
            case Configuration.ORIENTATION_LANDSCAPE:
                //horizontal
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                break;
        }
        */

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> av, View v,int pos, long id) {
                String t = lv.getItemAtPosition(pos).toString();
                if(horizontal){
                    fDet.mostrar(null);
                }else{
                    Intent i=new Intent(ActividadLista.this,ActividadDetalle.class);
                    i.putExtra("texto", t);
                    startActivityForResult(i,ACTDETALLE);
                }
            }
        });
    }

    private final int ACTDETALLE = 1;
    private final int ACTEDITAR = 2;


    /*MENU*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnañadir) {
            Intent i = new Intent(this,ActividadEditar.class);
            Bundle b=new Bundle();
            b.putInt("index", -1);//el indice del arraylist
            i.putExtras(b);
            startActivityForResult(i, ACTEDITAR);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Este método es el que muestra el menú contextual al realizar una pulsación larga sobre el elemento.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalle, menu);
    }

    //Long click para utilizar el menu contextual
    public boolean onContextItemSelected(MenuItem item) {
        //Obtener el item del listView
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //posicion selecicionada
        final int index= info.position;

        //Que hacemos con el item seleccionado:
        int id=item.getItemId();
        switch (id){
            case R.id.mnborrar:
                tostada("Borrado elemento "+va.get(index).getId());
                va.remove(index);
                //Notificamos el ListView los cambios
                ad.notifyDataSetChanged();
                xml();
                break;
            case R.id.mneditar:
                Intent i = new Intent(this,ActividadEditar.class);
                Bundle b=new Bundle();
                b.putInt("index", index);//el indice del arraylist
                b.putSerializable("vivienda", va.get(index));//la vivienda a aeditar
                //b.putSerializable("viviendas", va);
                i.putExtras(b);
                startActivityForResult(i, ACTEDITAR);
                break;
        }
        return super.onContextItemSelected(item);
    }

    /***/
    //A la vuelta del los intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tostada("hola: "+requestCode);
        switch (requestCode){
            case ACTDETALLE:
                String s=data.getStringExtra("texto");

                break;
            case ACTEDITAR:

                if (resultCode == Activity.RESULT_OK) {

                    Vivienda v=(Vivienda)data.getSerializableExtra("vivenda");
                    int i=data.getIntExtra("i",0);
                    if(comprobarExiste(v)){
                        alertYaExiste(i,v);
                    }else {
                        if (i == -1) {//si estamos añadiendo:
                            v.setId(va.size()-1);
                            va.add(v);
                        } else {//si estamos editando: borramos y añadimos con los cambios
                            va.remove(i);
                            va.add(i,v);
                        }
                    }
                }else{
                    tostada("miausnasda");
                }
                break;
        }
        xml();
        ad = new Adaptador(this, R.layout.elemento, va);
        lv.setAdapter(ad);
    }

    public void alertYaExiste(final int i, final Vivienda vivienda){
        boolean cambiar=true;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo, null);

        ((TextView) vista.findViewById(R.id.pregunta)).setText("La vivienda: "+ vivienda.mostrar2()+ " ya esta en venta por: "
        + vivienda.getPrecio() +"\n"+
        "¿Quieres cambiar el precio por: "+ va.get(i).getPrecio()+"?");
        vista.findViewById(R.id.aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                va.get(i).setPrecio(vivienda.getPrecio());
                tostada("Precio cambiado");
            }
        });
        vista.findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alert.setView(vista);
        alert.show();
    }

    public boolean comprobarExiste(Vivienda v){
        for(int i=0; i<va.size(); i++){
            if(va.get(i).equals(v)){
                return true;
            }
        }
        return false;
    }

    /*Tostada*/

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /*XML*/

    public void xml(){
        try {

            File f=new File(getExternalFilesDir(null),"viviendas.xml");
            //Preparamos el archivo
            FileOutputStream fosxml = new FileOutputStream(f);
            //Preparamos el documento XML
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            crearxml(docxml);
            fosxml.close();
            tostada("guardado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearxml(XmlSerializer docxml){

        try {

            //Creamos las etiquetas y cerramos el documento
            docxml.startTag(null, "inmobiliaria");
            for (int i = 0; i < va.size(); i++) {
                docxml.startTag(null, "vivienda");
                docxml.attribute(null, "tipo", va.get(i).getTipo());
                docxml.startTag(null, "localidad");
                docxml.text(va.get(i).getLocalidad());
                docxml.endTag(null, "localidad");
                docxml.startTag(null, "calle");
                docxml.attribute(null, "numero", va.get(i).getNumero());
                docxml.text(va.get(i).getCalle());
                docxml.endTag(null, "calle");
                docxml.startTag(null, "precio");
                docxml.text(va.get(i).getPrecio());
                docxml.endTag(null, "precio");
                docxml.endTag(null, "vivienda");
            }
            docxml.endDocument();
            docxml.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void leerxml(){
        XmlPullParser lectorxml = Xml.newPullParser();
        try {
            lectorxml.setInput(new FileInputStream(new File( getExternalFilesDir(null),"viviendas.xml")),"utf-8");
            int evento = lectorxml.getEventType();
            int i=0;
            Vivienda v=new Vivienda();
            while (evento != XmlPullParser.END_DOCUMENT) {
                String etiqueta = lectorxml.getName();
                if (evento == XmlPullParser.START_TAG) {
                    if(etiqueta.equalsIgnoreCase("vivienda")){
                        v=new Vivienda();
                        v.setId(i);
                        v.setTipo(lectorxml.getAttributeValue(null,"tipo"));
                    }else if (etiqueta.equalsIgnoreCase("localidad")) {
                        v.setLocalidad(lectorxml.nextText());
                    } else if (etiqueta.equalsIgnoreCase("calle")) {
                        v.setCalle(lectorxml.nextText());
                        v.setNumero(lectorxml.getAttributeValue(null, "numero"));
                    } else if (etiqueta.equalsIgnoreCase("precio")) {
                        v.setPrecio(lectorxml.nextText());
                        v.setImagenes(null);
                        va.add(v);
                        i++;
                    }
                }
                evento = lectorxml.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            tostada("IOException");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            tostada("pullparser");
        }
    }
}
