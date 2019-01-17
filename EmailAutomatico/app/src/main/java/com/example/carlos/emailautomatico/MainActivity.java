package com.example.carlos.emailautomatico;

import android.app.ProgressDialog;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//https://www.youtube.com/watch?v=i-7tUdtFbIg
//http://es.stackoverflow.com/questions/21574/error-al-intentar-env%C3%ADar-un-email-android-os-networkonmainthreadexception/21581

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button enviar;
    private EditText destino,asunto,cuerpo;
    private String dest,asun,cuer;

    ProgressDialog pdialog = null;

    private Session session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enviar = (Button) findViewById(R.id.btn_submit);
        enviar.setOnClickListener(this);
        destino = (EditText) findViewById(R.id.et_to);
        asunto = (EditText) findViewById(R.id.et_sub);
        cuerpo = (EditText) findViewById(R.id.et_text);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                dest = destino.getText().toString();
                asun = asunto.getText().toString();
                cuer = cuerpo.getText().toString();

                Properties props = new Properties();
                props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.port",25);
                props.setProperty("mail.smtp.user", "ejemplo@gmail.com");
                props.setProperty("mail.smtp.auth", "true");

                session = Session.getDefaultInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("carlosgarrido993@gmail.com", "cerveza11");
                    }
                });

                pdialog = ProgressDialog.show(this, "", "Sending Mail...", true);

                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
                break;
        }
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("carlosgarrido993@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
                message.setSubject(asun);

                DataHandler dh = new DataHandler(cuer,"text/plain");
                message.setDataHandler(dh);

                Transport.send(message);

                Log.d("Prueba","Prueba");
            } catch(MessagingException e) {
                e.printStackTrace();
                Log.d("Prueba", "Error");
            } catch(Exception e) {
                e.printStackTrace();
                Log.d("Prueba", "Error");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            destino.setText("");
            cuerpo.setText("");
            asunto.setText("");
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }

}
