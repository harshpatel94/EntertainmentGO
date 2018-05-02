package com.example.hunter.finalproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    CoordinatorLayout snackbarLayout;
    Button btn;
    EditText pass;
    EditText email;
    EditText uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_sign_up);

        uname = (EditText) findViewById(R.id.proj_u);
        email = (EditText) findViewById(R.id.proj_e);
        pass = (EditText) findViewById(R.id.proj_p);
        btn = (Button) findViewById(R.id.proj_b);
        snackbarLayout = (CoordinatorLayout)findViewById(R.id.snackbarLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unameStr = uname.getText().toString();
                String emailStr = email.getText().toString();
                String passStr = pass.getText().toString();
                Log.d("String Send>>>",unameStr +"  "+ emailStr +"  "+ passStr);
                Register(unameStr,emailStr,passStr);
            }
        });
    }

    private void Register(String username,String emailAdd,String password){
        String url = "http://10.0.2.2:8080/users/register";
        JSONObject js = new JSONObject();
        Log.d("String>>>",username +"  "+ emailAdd +"  "+ password);
        try{
            js.put("userName",username);
            js.put("emailId",emailAdd);
            js.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.d("Json data>>",js.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                url, js,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response>>>", response.toString());
                        pDialog.hide();
                        final Snackbar snackbar = Snackbar.make( snackbarLayout, "Successfully Registered. ", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                                startActivity(intent);
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("String:>>>", "Error: " + error.getMessage());
                pDialog.hide();
                final Snackbar snackbar = Snackbar.make( snackbarLayout, "Something Went wrong: "+error.getMessage(), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUp.this,LoginActivity.class);
                        startActivity(intent);
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }) {


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
}
