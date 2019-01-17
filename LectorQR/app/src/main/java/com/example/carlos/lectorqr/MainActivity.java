package com.example.carlos.lectorqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

//https://www.youtube.com/watch?v=e5pI6CSxAX8/

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button qr;
    private TextView codigo;
    private Button generarQr;
    private EditText code;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qr = (Button) findViewById(R.id.btnQR);
        qr.setOnClickListener(this);

        codigo = (TextView) findViewById(R.id.textView);

        generarQr = (Button) findViewById(R.id.btn_generarQR);
        generarQr.setOnClickListener(this);

        code = (EditText) findViewById(R.id.et_code);

        image = (ImageView) findViewById(R.id.iv_image);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnQR:
                Intent intent = new Intent(getApplicationContext(),SimpleScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_generarQR:
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode("text2Qr", BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);

                }catch (WriterException e){
                    e.printStackTrace();
                }


                /*Bundle bundle = new Bundle();
                bundle.putString(ContactsContract.Intents.Insert.NAME, "Daniel Alvarez");
                bundle.putString(ContactsContract.Intents.Insert.PHONE, "77242424");
                bundle.putString(ContactsContract.Intents.Insert.EMAIL, "daniel@alvarez.tech");

                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.addExtra("ENCODE_DATA", bundle);
                integrator.shareText(bundle.toString(), "CONTACT_TYPE");
                break;*/
        }
    }
}
