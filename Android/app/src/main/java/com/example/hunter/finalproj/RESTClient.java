package com.example.hunter.finalproj;

import com.facebook.CallbackManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.util.List;

import static android.R.attr.handle;

/**
 * Created by HUNTER on 4/23/2017.
 */

public class RESTClient {
    private AsyncHttpClient client;
    private final String API_URL = "http://localhost:8080/";


    public RESTClient(){
        this.client = new AsyncHttpClient();
    }

    private String call(String url){
        return API_URL + url;
    }

    public void getMovies(JsonHttpResponseHandler handler){
        String url = call("titles/");
        RequestParams params = new RequestParams();
        client.get(url,params,handler);
    }


}
