package com.ecom.ecommerceapp.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecom.ecommerceapp.ApiInterfaces.StoreApi;
import com.ecom.ecommerceapp.ApiServices.ApiServices;
import com.ecom.ecommerceapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText et_email;
    EditText et_password;
    Button btn_login;
    private StoreApi storeApi;
    ProgressDialog progressDialog;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        et_email = (EditText) findViewById(R.id.et_email1);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading....");

        btn_login.setOnClickListener(view -> {
            loginUser();
        });


        storeApi = ApiServices.ApiService.getInstance();

    }

    private void loginUser() {

        if (auth.getCurrentUser() == null) {
            auth.createUserWithEmailAndPassword(UUID.randomUUID().toString() + "@gmail.com", UUID.randomUUID().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                }
            });
        }
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
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Log.d("TAG", "onResponse: " + obj.getString("token"));
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
