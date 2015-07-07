/*
Proyecto de Aplicaciones M�viles de la Escuela Polit�cnica
Nacional de la Facultad de Ingenier�a en Sistemas:
Integrantes:
Calo Dar�o
C�rdenas Hugo
Chipantasi Wilson
Padilla Lorena

Prerrequisitos
En la Carpeta "drawable" del proyecto vamos a poner el icono de la aplicacion rimay.png

Descripci�n
En la siguiente codigo se describe una aplicaci�n m�vil, en donde se tiene el explorador de archivos
en la memoria del tel�fono.
La aplicaci�n se realiz� en Android Studio 1.2.2.
*/
package com.example.rimay.rimay3;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//vamos a crear una clase llamada explorador
public class Explorador extends ListActivity {

    //Declramos de forma privada lo siguiente:
    private List<String> listaNombresArchivos;
    private List<String> listaRutasArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;

    //En el siguiente bloque de cofigo vamos a llamar al xml principal del explorador de archivos
    // Y el nombre que va a tener el texto en donde se despliga los nombres de las carpetas que tiene
    //el almacenamiento interno del tel�fono.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se tiene el xml endonde se va tener los componentes que se va a mostrar en la aplicaci�n.
        setContentView(R.layout.activity_explorador);
        //en la aplicaci�n muestra la navegaci�n que se va a realizar en la aplicaci�n.
        carpetaActual = (TextView) findViewById(R.id.rutaActual);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();

        verArchivosDirectorio(directorioRaiz);
    }


    /*en el siguiente c�digo bos va a mostrar en un listview creado los archivos o carpetas que
    tiene el tel�fono dependiendo de la ruta actual.
    * */
    private void verArchivosDirectorio(String rutaDirectorio) {
        carpetaActual.setText("Se encuentra en: " + rutaDirectorio);
        listaNombresArchivos = new ArrayList<String>();
        listaRutasArchivos = new ArrayList<String>();
        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles();
        int x = 0;

        /*
        * Vamos a crear un elmento el cual nos va a permitir regresar si no es el directorio que
        * estamos buscando.
        * */
        if (!rutaDirectorio.equals(directorioRaiz)) {
            listaNombresArchivos.add("..../");
            listaRutasArchivos.add(directorioActual.getParent());
            x = 1;
        }

        /*
        * Vamos a tener las rutas de los archivos y carpetas.
        * */
        for (File archivo : listaArchivos) {
            listaRutasArchivos.add(archivo.getPath());
        }

        /*
        * en el siguiente codigo nos va a permitir ordenar las carpetas y archivos en orden Alfab�tico
        * */
        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);


        /*
        * Se va a recorrer la lista de archivos que esta ordenada alfab�ticamente para poder
        * mostrar en el list view de la aplicaci�n.
        * */

        for (int i = x; i < listaRutasArchivos.size(); i++){
            File archivo = new File(listaRutasArchivos.get(i));
            if (archivo.isFile()) {
                listaNombresArchivos.add(archivo.getName());
            } else {
                listaNombresArchivos.add("_/_" + archivo.getName());
            }
        }

        /*
        * Se va a indicar si el carpeta no se ha econtrado algun archivo o carepta
        * de la siguiente manera.
        * */
        if (listaArchivos.length < 1) {
            listaNombresArchivos.add("No se ha encontrado ning�n archivo o carpeta");
            listaRutasArchivos.add(rutaDirectorio);
        }

        /*
        * Vamos a crear un ArrayAdapter el cual es un adaptador en donde se va a asigna la lista
        * de nombres de los archivos o carpetas y el layour en donde van a estar los elementos de
        * la lista.
        * */
        adaptador = new ArrayAdapter<String>(this,
                R.layout.text_view_lista_archivos, listaNombresArchivos);
        setListAdapter(adaptador);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        /*
        *En esta parte del c�digo vamos a tener la ruta del archivo o carpeta en donde hacemos
        * clic en el listview.
        * */

        File archivo = new File(listaRutasArchivos.get(position));

        /*
        En esta parte de c�digo va a mostrar en forma de popup un mensaje en donde nos va a
        decir el archivo que hemos seleccionado y se va a cargar los archivos que contiene
        el list view
        * */
        if (archivo.isFile()) {
            Toast.makeText(this,
                    "Se ha seleccionado el siguiente archivo: " + archivo.getName(),
                    Toast.LENGTH_LONG).show();
        } else {
            /*
            * Si no es un archivo vamos a mostrar los archivos que contiene el directorio.
            * */
            verArchivosDirectorio(listaRutasArchivos.get(position));
        }

    }

}