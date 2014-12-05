package com.izv.fragmentosorientacion;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


public class Vivienda implements Serializable, Comparable{

    private int id;
    private String localidad, calle, numero, tipo, precio;
    private ArrayList<String> imagenes;

    public Vivienda() {
    }

    public Vivienda(int id, String localidad, String calle, String numero, String tipo, String precio, ArrayList<String> imagenes) {
        this.id = id;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.imagenes = imagenes;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return "Vivienda{" +
                "id=" + id +
                ", localidad='" + localidad + '\'' +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }

    public String mostrar() {
        return  " C/" + calle + ", nº=" + numero +
                ", "+ localidad +
                ", tipo=" + tipo +
                ", precio=" + precio;
    }

    public String mostrar2() {
        return  "El/La "+ tipo +
                " en  C/" + calle + ", nº=" + numero +
                ", " + localidad;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vivienda)) return false;

        Vivienda vivienda = (Vivienda) o;

        if (calle != null ? !calle.equals(vivienda.calle) : vivienda.calle != null) return false;
        if (localidad != null ? !localidad.equals(vivienda.localidad) : vivienda.localidad != null)
            return false;
        if (numero != null ? !numero.equals(vivienda.numero) : vivienda.numero != null)
            return false;
        if (tipo != null ? !tipo.equals(vivienda.tipo) : vivienda.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }


}



/*
-id
    Direccion:
-localidad
-calle
-n
-tipo
-precio
*/



    /*

    El vendedor debe de tener en su móvil, muebles en venta,
guardados en base de datos, xml etc...
Información mínima a guardar:
-ID
-Dirección: localidad, calle + número
-tipo (cochera, vivienda...)
-Precio

Debemos de permitir añadir, modificar, borrar...

En el fragmento de la derecha, queremos mostrar fotos 1 (min).
En funcion de la orientacion que se muestren o un fragmento o
dos fragmentos. Guardar en memoria externa privada. Las fotos
se llamarán del siguiente modo: 'inmueble'_id_aaaa_mm_dd_hh_mm_ss.jpg

*/