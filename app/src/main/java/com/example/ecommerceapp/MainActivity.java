package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText et_email;
    EditText et_password;
    Button btn_login;
    private StoreApi storeApi;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_email = (EditText) findViewById(R.id.et_email1);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading....");

        btn_login.setOnClickListener(view -> {
            loginUser();
        });
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
        storeApi = ApiServices.ApiService.getInstance();

    }

    private void loginUser() {
        progressDialog.show();
            /*JSONObject paramObject = new JSONObject();
            paramObject.put("username", et_email.getText().toString());
            paramObject.put("password", et_password.getText().toString());*/

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", et_email.getText().toString());
        jsonParams.put("password", et_password.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<ResponseBody> loginUser = storeApi.loginUser(body);
        loginUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.body() == null) {
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Log.d("TAG", "onResponse: " + obj.getString("token"));
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
