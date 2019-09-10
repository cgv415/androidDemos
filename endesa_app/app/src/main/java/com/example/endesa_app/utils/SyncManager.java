package com.example.endesa_app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.suratica.auditoria_movil.HomeActivity;*/

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.endesa_app.models.About;
import com.example.endesa_app.models.Account;
import com.example.endesa_app.models.Privacy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SyncManager {

    public static Context mContext;
    public static ProgressBar pb_progress;
    public static TextView tv_progress;

    public final static int METHOD = Request.Method.GET;
    public final static String SERVER =  "https://cuentapasosapp.com/api/v1/";
    public final static String LOGIN =  "auth/login";
    public final static String ABOUT =  "pages/1";
    public final static String PRIVACY =  "pages/2";

    public static AlertDialog dialog;
    public static int progress;
    public static boolean finish;
    public static boolean updated;

    /*public static String loadJSONFromAsset(Context ctx, String file) {
        String json = null;
        try {
            Activity activity = (Activity) ctx;
            InputStream is =  activity.getAssets().open(file + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }*/

    /*public static void popUpSetPosition(final ArrayList<Support> supports,final Support support)throws Exception{

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View v = inflater.inflate(R.layout.popup_nearby_supports, null);
        ListView nearby_supports = v.findViewById(R.id.lv_nearby_supports);



        NearbySupportsAdapter adapter = new NearbySupportsAdapter((Activity) mContext,supports);
        nearby_supports.setAdapter(adapter);

        nearby_supports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Support newLoc = supports.get(position);
                support.setLat(newLoc.getLat());
                support.setLng(newLoc.getLng());
                support.update();


                dialog.cancel();
                *//*Intent intent = new Intent(getApplicationContext(),DetailProjectActivity.class);
                intent.putExtra("project_id",project_id);
                finish();
                startActivity(intent);*//*

            }
        });

        builder.setView(v);
        builder.setPositiveButton(R.string.use_location, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(LocManager.located){
                    LatLng pos = LocManager.currentPos;
                    support.setLat(String.valueOf(pos.latitude));
                    support.setLng(String.valueOf(pos.longitude));
                    support.update();
                    Toast.makeText(v.getContext(),"Ubicacion establecida correctamente",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(v.getContext(),"Ubicación GPS no establecida.",Toast.LENGTH_LONG).show();
                }
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        dialog = builder.show();
    }*/


    /*Subida de datos*/
   /* public static boolean upload(Context context){
        try{
            finish = false;
            popUpProgressBarUpload(context);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
            failed("upload",e.getMessage());
            return false;
        }
    }*/

    /*public static JSONObject getJSONAnswers(ArrayList<Answer> answers)throws Exception{
        IncidentType incident = new IncidentType();
        JSONObject incidenObject = new JSONObject();
        JSONObject jsonAnswers = new JSONObject();
        JSONObject body = new JSONObject();
        JSONArray ids = new JSONArray();

        body.put("ids",ids);

        JSONObject data = new JSONObject();

        JSONArray support_groups_data = new JSONArray();
        JSONArray fields_groups_data = new JSONArray();
        JSONArray incident_groups_data = new JSONArray();
        JSONArray svc_groups_data = new JSONArray();

        for(int i = 0;i < answers.size(); i++){
            JSONObject object = new JSONObject();

            Answer ans = answers.get(i);
            object.put("project_id",ans.getProject().getId());
            object.put("support_id",ans.getSupport().getId());
            object.put("fields_group_id",ans.getFieldsGroup().getId());
            object.put("field_id",ans.getField().getId());
            object.put("value",ans.getValue());

            String fieldgroupid = ans.getFieldsGroup().getId();
            if(!AppManager.isNumeric(fieldgroupid)){
                switch (fieldgroupid){
                    case "incidents_groups":
                        if(ans.getField().getId().equals("description")){
                            incidenObject = object;
                            incident.setDescription(ans.getValue());
                        }else{
                            IncidentType incidentTypeType = (IncidentType) IncidentType.find(new String[]{"name"},new String[]{ans.getValue()});
                            incident.setName(ans.getValue());
                            if(incidentTypeType != null){
                                incident.setId(incidentTypeType.getId());
                            }
                        }
                        break;
                    case "svc_groups":
                        SVCParameter parameter = (SVCParameter) SVCParameter.find("input_name",ans.getField().getId());
                        if(parameter != null){
                            object.put("field_id",parameter.getId());
                            SVCParameterOption svcParameterOption = (SVCParameterOption) SVCParameterOption.findFirst(new String[]{"option"},new String[]{ans.getValue()});
                            if(svcParameterOption != null){
                                JSONObject svcObject = new JSONObject();
                                svcObject.put("project_id",ans.getProject().getId());
                                svcObject.put("support_side_id",ans.getSupport().getSideID());
                                svcObject.put("svc_parameter_id",parameter.getId());
                                svcObject.put("svc_parameter_option_id",svcParameterOption.getId());
                                svcObject.put("value",svcParameterOption.getValue());

                                svc_groups_data.put(svcObject);
                            }

                        }
                        break;
                    case "support_groups":
                        if(ans.getField().getId().equals("support_type_id")){
                            SupportType supportType = (SupportType) SupportType.find(new String[]{"name"},new String[]{ans.getValue()});
                            object.put("value",supportType.getId());

                        }
                        support_groups_data.put(object);
                        break;
                }
            }else{
                fields_groups_data.put(object);
            }

        }

        incidenObject.put("type_id",incident.getId());
        int size = incidenObject.length();
        if(incidenObject.length() !=0 && !incidenObject.getString("value").equals("")){
            incident_groups_data.put(incidenObject);
        }

        data.put("support_fields_data",support_groups_data);
        data.put("fields_data",fields_groups_data);
        data.put("incident_fields_data",incident_groups_data);
        data.put("svc_fields_data",svc_groups_data);

        body.put("data",data);
        body.put("sync_date",AppManager.getDateTime());

        jsonAnswers.put("answers",body);

        return jsonAnswers;
    }*/

    /*public static void callApiUploadAnsers(final String service, final JSONObject jsonAnswers)throws Exception{
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = SERVER + UPLOAD + service;
        StringRequest sr = new StringRequest(METHOD, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            updateProgress(1);
                            uploadImages();
                        }catch (Exception e){
                            e.printStackTrace();
                            failed(e.getMessage(),"upload");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failed(error.getMessage(),"upload");
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("api_token",User.getToken());
                params.put("data",jsonAnswers.toString());
                params.put("date",User.getLastUpdate());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }*/

    /*public static void uploadImages()throws Exception{
        ArrayList<Media> medias = AppManager.cast(Media.toUpload());
        if(medias.size() > 0){
            failedMedias = (ArrayList<Media>) medias.clone();
            callApiUploadImage("media",medias,0);
        }else{
            finished();
        }
    }*/

    /*public static void callApiUploadImage(final String service,final ArrayList<Media> medias, final int currentMedia)throws Exception{
        final Media media = medias.get(currentMedia);
        String url = SERVER + UPLOAD + service;
        String filePath = media.getPath();
        String filename=filePath.substring(filePath.lastIndexOf("/")+1);
        String extension=filename.substring(filename.lastIndexOf(".")+1);

        File file = new File(filePath);

        OkHttpClient client = new OkHttpClient();

        String mediaType = "image/" + extension;
        RequestBody  requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_token",User.getToken())
                .addFormDataPart("support_id",media.getSupport_id())
                .addFormDataPart("project_id",media.getProject_id())
                .addFormDataPart("date",User.getLastUpdate())
                .addFormDataPart("image", filename, RequestBody.create(MediaType.parse(mediaType), file))
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try{
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("Error",e.getMessage());
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    if(response.code() == 200){
                        media.updateUploadDate(AppManager.getDateTime());
                        failedMedias.remove(media);
                    }
                    updateProgress(currentMedia+2);

                    if(currentMedia < medias.size()-1){
                        try {
                            callApiUploadImage(service,medias,currentMedia+1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            failed(e.getMessage(),"upload");

                        }
                    }else{
                        //showFailedMedias();
                        finished();
                    }

                    String strResponse = response.toString();
                    Log.i("Response",strResponse);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
            failed(e.getMessage(),"upload");
        }
    }*/

    /*public static void showFailedMedias(){
        String[] actions = new String [failedMedias.size()];
        for(int i = 0 ; i < failedMedias.size() ; i++){
            actions[i] = failedMedias.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Error al subir las siguientes imágenes:")
                .setItems(actions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }*/

    /*Parte de descargas*/
    /*public static void popUpProgressBarDownload(final Context mContext) throws Exception{

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.popup_progress, null);

        pb_progress = view.findViewById(R.id.pb_progress);
        tv_progress = view.findViewById(R.id.tv_progress);

        builder.setTitle("Descargando datos");

        builder.setView(view);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                User.setLastDownload(AppManager.getDateTime());
                User.update();
                Intent intent = new Intent(mContext,HomeActivity.class);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        dialog.setCanceledOnTouchOutside(false);
        progress = 100/8;

        updateProgress(0);
    }
*/
    /*public static boolean download(Context context){
        try{
            finish = false;
            popUpProgressBarDownload(context);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            failed("download",e.getMessage());
            return false;
        }

    }*/

    /*public static void downloadIncidents()throws Exception{
        callApiDownload("incident_types");
    }*/

    /*public static void downloadSVCParameters()throws Exception{
        callApiDownload("svc_parameters");
    }*/

    /*public static void downloadLocations()throws Exception{
        callApiDownload("provinces");

    }*/

    /*public static void downloadSupportLevels()throws Exception{
        callApiDownload("support_types");
    }*/

    /*public static void downloadProjects()throws Exception{
        callApiDownload("projects");
    }*/

    /*public static void downloadSupports()throws Exception{
        callApiDownload("projects_supports");
    }*/

   /* public static void downloadAnswers()throws Exception{
        callApiDownload("get_audit_answers");
    }
*/
    /*public static void downloadGroups()throws Exception{
        callApiDownload("support_forms");
    }*/

    /*public static void callApiDownload(final String service)throws Exception{
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = SERVER + DOWNLOAD + service;
        StringRequest sr = new StringRequest(METHOD, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            switch (service){
                                case "provinces":
                                    Province.importer(object);
                                    updateProgress(1);
                                    downloadIncidents();
                                    break;
                                case "incident_types":
                                    IncidentType.importer(object);
                                    updateProgress(2);
                                    downloadSupportLevels();
                                    break;
                                case "support_types":
                                    Segment.importer(object);
                                    updateProgress(3);
                                    downloadSVCParameters();
                                    break;
                                case "svc_parameters":
                                    SVCParameter.importer(object);
                                    updateProgress(4);
                                    downloadProjects();
                                    break;
                                case "projects":
                                    Project.importer(object);
                                    updateProgress(5);
                                    downloadSupports();
                                    break;
                                case "projects_supports":
                                    Support.importer(object);
                                    updateProgress(6);
                                    downloadGroups();
                                    break;
                                case "support_forms":
                                    FieldsGroup.importer(object);
                                    updateProgress(7);
                                    downloadAnswers();
                                    break;
                                case "get_audit_answers":
                                    Answer.importer(object);
                                    updateProgress(8);
                                    finished();
                                    break;
                                case "get_supports_by_lat_lng":
                                    Support.nearbySupports(object);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            failed(e.getMessage(),"download");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        failed(error.getMessage(),"download");
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("api_token",User.getToken());
                params.put("date",User.getLastDownload());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
*/
    /*Parte comun*/
    public static void login(final Context context,String alias,final String password){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = SERVER + LOGIN;
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("alias", "CGV2");
            jsonObject.put("password", "suratica");
            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                String access_token = response.getString("access_token");
                            }catch (Exception e){
                                e.printStackTrace();
                                //Toast.makeText(context,context.getApplicationContext().getResources().getString(R.string.login_error),Toast.LENGTH_LONG).show();
                                Log.e("login",e.getMessage() + " ," + e.getLocalizedMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("X-Requested-With","XMLHttpRequest");
                    return params;
                }
            };
            queue.add(sr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void signup(final Context context,final String alias,final String password){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = SERVER + LOGIN;
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("alias", alias);
            jsonObject.put("password", password);
            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                String access_token = response.getString("access_token");

                                JSONObject accountObject = new JSONObject();
                                accountObject.put("id",1);
                                accountObject.put("alias",alias);
                                accountObject.put("password",password);
                                accountObject.put("avatar","0");
                                accountObject.put("postal_code","04007");
                                accountObject.put("access_token",access_token);
                                accountObject.put("active",true);

                                Account account = new Account(accountObject);
                                if(account.save()){
                                    Log.d("Signup","Cuenta insertada con exito");
                                }else{
                                    Log.e("Signup","Fallo Cuenta insertada");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                //Toast.makeText(context,context.getApplicationContext().getResources().getString(R.string.login_error),Toast.LENGTH_LONG).show();
                                Log.e("login",e.getMessage() + " ," + e.getLocalizedMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("X-Requested-With","XMLHttpRequest");
                    return params;
                }
            };
            queue.add(sr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getAbout(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);

        Account account = (Account) Account.findById("1");
        final String token = "Bearer " + account.getAccess_token();

        String url = SERVER + ABOUT;
        JSONObject jsonObject = new JSONObject();
        try{
            StringRequest sr = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object = new JSONObject(response);
                                JSONObject data = object.getJSONObject("data");
                                String id = data.getString("id");
                                String title = data.getString("title");
                                String content = data.getString("content");

                                JSONObject aboutObject = new JSONObject();
                                aboutObject.put("id",id);
                                aboutObject.put("title",title );
                                aboutObject.put("content",content );

                                About about = new About(aboutObject);
                                if(about.save()){
                                    Log.d("About","About insertada con exito");
                                }else{
                                    Log.e("About","Fallo About insertada");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                //Toast.makeText(context,context.getApplicationContext().getResources().getString(R.string.login_error),Toast.LENGTH_LONG).show();
                                Log.e("About",e.getMessage() + " ," + e.getLocalizedMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("X-Requested-With","XMLHttpRequest");
                    params.put("Authorization",token);

                    return params;
                }
            };
            queue.add(sr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getPrivacy(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);

        Account account = (Account) Account.findById("1");
        final String token = "Bearer " + account.getAccess_token();

        String url = SERVER + PRIVACY;
        JSONObject jsonObject = new JSONObject();
        try{
            StringRequest sr = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object = new JSONObject(response);
                                JSONObject data = object.getJSONObject("data");
                                String id = data.getString("id");
                                String title = data.getString("title");
                                String content = data.getString("content");

                                JSONObject privacyObject = new JSONObject();
                                privacyObject.put("id",id);
                                privacyObject.put("title",title );
                                privacyObject.put("content",content );

                                Privacy privacy = new Privacy(privacyObject);
                                if(privacy.save()){
                                    Log.d("Privacy","Privacy insertada con exito");
                                }else{
                                    Log.e("Privacy","Fallo Privacy insertada");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                //Toast.makeText(context,context.getApplicationContext().getResources().getString(R.string.login_error),Toast.LENGTH_LONG).show();
                                Log.e("Privacy",e.getMessage() + " ," + e.getLocalizedMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("X-Requested-With","XMLHttpRequest");
                    params.put("Authorization",token);

                    return params;
                }
            };
            queue.add(sr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void start(Context ctx){
        mContext = ctx;
    }

    /*public static void updateProgress(int increment){
        String percentage = "% completado";
        int newProgress = increment == 100?increment:progress*increment;

        tv_progress.setText(newProgress + percentage);
        pb_progress.setProgress(newProgress);
    }*/

    /*public static void failed(String message,String type){
        Log.e(type, "error: " + message);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
        Toast.makeText(mContext,"Error de sincronización",Toast.LENGTH_LONG).show();
    }

    public static void finished(){
        updateProgress(100);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }*/
}
