package com.izv.fragmentosorientacion;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

public class FragmentoDetalle extends Fragment {

    private View v;
    public FragmentoDetalle() {
    }
    private ImageView img;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmento_detalle, container, false);
        img = (ImageView)getView().findViewById(R.id.imageView);
        return v;
    }

    public void mostrar(String nombre){
        File f = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +"/"+nombre);
        Bitmap imagen = BitmapFactory.decodeFile(f.getAbsolutePath());
        img.setImageBitmap(imagen);
    }



/*
    private boolean guardarImagen (URL url){
        if(!url.equals(null)) {
            try {
                URLConnection urlCon = url.openConnection();
                InputStream is = urlCon.getInputStream();
                FileOutputStream fos = new FileOutputStream(f);
                byte[] by = new byte[1000];
                int temp = is.read(by);
                while (temp > 0) {
                    fos.write(by, 0, temp);
                    temp = is.read(by);
                }
                is.close();
                fos.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
*/
}
