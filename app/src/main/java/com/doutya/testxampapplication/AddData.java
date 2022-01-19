package com.doutya.testxampapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddData extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://192.168.1.4:80/testapp/addData.php";
  //  private static final String REGISTER_REQUEST_URL = "http://10.0.2.2/testapp/addData.php";

   //private static final String REGISTER_REQUEST_URL = "https://b0a5-45-115-91-138.ngrok.io";


    //private static final String REGISTER_REQUEST_URL = "https://communityhq.co/rent/addData.php";

    private Map<String,String> params;

    public AddData(String textname,  Response.Listener<String> listener, Response.ErrorListener res) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, res);
        params = new HashMap<>();
        params.put("textname", textname);

    }

    public Map<String,String> getParams(){
        return params;
    }

}