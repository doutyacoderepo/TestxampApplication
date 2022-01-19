package com.doutya.testxampapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.doutya.mediacontroller.ImportMedia;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ImportMedia imagePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bAdd = (Button) findViewById(R.id.bAdd);
        Button ChooseMedia = (Button) findViewById(R.id.ChooseMedia);
        EditText tvText = (EditText) findViewById(R.id.tvText);

        ChooseMedia.setOnClickListener(View->{
            imagePicker = new ImportMedia(MainActivity.this);
            imagePicker.GetMedia(101);
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("feli", "clicked in");
                Response.Listener<String> responseListener = new Response.Listener <String>() {
                    @Override
                    public void onResponse(@NonNull String response) {
                        try {
                            Log.e("feli", "Logged in"+response.toString());
                            JSONObject jsonResponse = new JSONObject(response);



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }

                };

                Response.ErrorListener responsel= new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the reuest has either time out or there is no connection

                        } else if (error instanceof AuthFailureError) {
                            // Error indicating that there was an Authentication Failure while performing the request

                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response

                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request

                        } else if (error instanceof ParseError) {
                            // Indicates that the server response could not be parsed

                        }
                    }
                };


                AddData addData = new AddData(tvText.getText().toString(),  responseListener, responsel);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(addData);
            }
        });
    }
}