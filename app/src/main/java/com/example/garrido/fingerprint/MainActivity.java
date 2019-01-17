package com.example.garrido.fingerprint;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private String KEY_NAME = "somekeyname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if(!fingerprintManager.isHardwareDetected()){
            Toast.makeText(this,"Finger print not detected",Toast.LENGTH_LONG).show();
            Log.e("Hardware","Finger print not detected");
            return;
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Fingerprint permission rejected",Toast.LENGTH_LONG).show();
        }

        if(!keyguardManager.isKeyguardSecure()){
            Toast.makeText(this,"Keyguard not enabled",Toast.LENGTH_LONG).show();
        }

        KeyStore keyStore = null;

        try{
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        }catch (Exception e){
            Toast.makeText(this,"KeyStore: " + e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        KeyGenerator keyGenerator = null;
        try{
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        }catch (Exception e){
            Toast.makeText(this,"KeyGenerator: " + e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        try{
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();
        }catch (Exception e){
            Toast.makeText(this,"Generating keys: " + e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        Cipher cipher;

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES
                    + "/" + KeyProperties.BLOCK_MODE_CBC
                    + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }catch (Exception e){
            Toast.makeText(this,"Cipher: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }

        try{
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
        }catch (Exception e){
            Toast.makeText(this,"Secret Key : " + e.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }

        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,new AuthenticationHandler(this),null);
    }
}
