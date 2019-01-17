package com.example.carlos.lectorqr;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


//Generador
//https://github.com/dm77/barcodescanner
//Lector
//https://www.youtube.com/watch?v=W6-kK6tAML4

public class SimpleScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView mScannerView;
    private String TAG = "Result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scanner);

        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        //Scan results
        final String code = result.getContents();
        //Scan format (qr code, pdf417, etc)
        final String format = result.getBarcodeFormat().getName();
        // Concat
        final String fullMessage = "Contents: "+ code + ", Format: "+ format;

        Toast.makeText(getApplicationContext(),fullMessage,Toast.LENGTH_SHORT).show();

        try{
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),notification);
            r.play();

        }catch (Exception e){
            Log.e(TAG,e.getLocalizedMessage());
        }
        //showMessageDialog(fullMessage);

    }

    /*public void showMessageDialog(String message){
        DialogFragment fragment = MessageDialogFragment.newInstance("Scan results",message,this);
        fragment.show(getSupportFragmentManager(),"");
    }*/
}
