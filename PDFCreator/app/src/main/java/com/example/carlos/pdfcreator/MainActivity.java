package com.example.carlos.pdfcreator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import harmony.java.awt.Color;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

//https://amatellanes.wordpress.com/2013/03/13/ejemplo-sencillo-de-creacion-de-un-pdf-en-android/
//https://sintaxispragmatica.wordpress.com/2013/07/18/anadir-librerias-jar-en-androidstudio/
public class MainActivity extends AppCompatActivity {

    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String NOMBRE_DIRECTORIO ="/MiPdf";
    private Button crear;
    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        crear = (Button) findViewById(R.id.btnGenerar);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generarDocumento();
                createPDF();
            }
        });

    }

    public void createPDF()
    {
        Document doc = new Document();


        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/droidText";
//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            /*String path = Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();*/
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);


            File file = new File(dir, "sample.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();


            Paragraph p1 = new Paragraph("Hi! I am generating my first PDF using DroidText");
            Font paraFont= new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

            Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
            Font paraFont2= new Font(Font.COURIER,14.0f);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);

            doc.add(p2);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);

            //add image to document
            doc.add(myImg);

            //set footer
            Phrase footerText = new Phrase("This is an example of a footer");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            doc.setFooter(pdfFooter);

            Toast.makeText(getApplicationContext(),"documento creado en " + path,Toast.LENGTH_SHORT).show();

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }


    }

    public void createPDF1(){
        Document doc = new Document();

        try{
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/droidText";
            String path = Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/droidText";
            File dir = new File(path);
            if(!dir.exists()){
                if(dir.mkdirs()){
                    Toast.makeText(getApplicationContext(),"Documento creado documento",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Documento fallido en crear",Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getApplicationContext(),"Documento fallido",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Documento fallido en cerrar documento",Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    public void generarDocumento(){
        try{
            // Creamos el documento.
            Document documento = new Document();
            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero(NOMBRE_DOCUMENTO);

            // Creamos el flujo de datos de salida para el fichero donde guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter.getInstance(documento, ficheroPdf);

            // Abrimos el documento.
            documento.open();

            agregarTexto(documento);
            agregarImagen(documento);
            cerrarDocumento(documento);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Documento fallido en generar documento",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void cerrarDocumento(Document documento){
        try{
            // Cerramos el documento.
            documento.close();
            Toast.makeText(getApplicationContext(),"Documento creado",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Documento fallido en cerrar documento",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void agregarTexto(Document documento){
        try{
            // Añadimos un título con la fuente por defecto.
            documento.add(new Paragraph("Título 1"));

            // Añadimos un título con una fuente personalizada.
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                    Font.BOLD, Color.RED);
            documento.add(new Paragraph("Título personalizado", font));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Documento fallido en agregar texto",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void agregarImagen(Document documento){
        try{
            // Insertamos una imagen que se encuentra en los recursos de la aplicación.
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            documento.add(imagen);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Documento fallido en agregar imagen",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
