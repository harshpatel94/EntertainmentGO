package com.example.hunter.finalproj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String S_userId = "userId";
    public static final String S_username = "userName";
    public static final String S_emailId = "emailId";


    public LoginActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.btn_login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.pass);

        final String emailStr = email.getText().toString();
        final String passStr = pass.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SendRequest(emailStr,passStr);
            }
        });
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                getUserInfo(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        TextView tv = (TextView) findViewById(R.id.text_signup);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Log.e("data:",data.toString());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    protected void getUserInfo(LoginResult loginResult) {
        GraphRequest dataRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                //save in shared prefs
                getSharedPreferences("fbPrefs", MODE_PRIVATE).edit().putString("profile", object.toString()).apply();
                Log.d("Profile object",object.toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //add permissions to access fields in profile info json response
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        Log.d("String:>>>",permission_param.toString());
        dataRequest.setParameters(permission_param);
        dataRequest.executeAsync();
    }

    public void SendRequest(String email,String pass) {

        String url = "http://10.0.2.2:8080/users/login";
        JSONObject js = new JSONObject();
        try{
           js.put("emailId",email);
           js.put("password",pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                url, js,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response>>>", response.toString());
                        try {
                            String userid = response.getString("userId");
                            String emailid = response.getString("emailId");
                            String uname = response.getString("userName");
                            session(userid,emailid,uname);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String:>>>", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("emailId", "harsh@gmail.com");
//                params.put("password", "abcd");
//
//                return params;
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    private void session(String userid,String emailid,String username){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(S_userId,userid);
        editor.putString(S_emailId,emailid);
        editor.putString(S_username,username);
        editor.commit();
        editor.apply();
        return;
    }


}



