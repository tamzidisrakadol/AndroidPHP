package com.example.androiddatabasephp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, emailEdiText, passwordEditText;
    private Button submitbtn;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(MainActivity.this,profileActivity.class);
            startActivity(intent);
            return;
        }
        usernameEditText=findViewById(R.id.usernameEditText);
        emailEdiText=findViewById(R.id.emailEdittext);
        passwordEditText=findViewById(R.id.passEditText);
        loginText=findViewById(R.id.textView);
        submitbtn=findViewById(R.id.submitBTn);

    submitbtn.setOnClickListener(v -> {
        if (isdataValid()){
            registerUser();
        }
    });
    loginText.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this,loginActivity.class);
        startActivity(intent);
        finish();
    });

    }

    private void registerUser() {
        String username=usernameEditText.getText().toString();
        String email = emailEdiText.getText().toString();
        String password=passwordEditText.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url_Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",username);
                map.put("email",email);
                map.put("password",password);
                return map;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean isdataValid() {
        if (usernameEditText.getText().toString().isEmpty()){
            usernameEditText.setError("please enter your username");
            usernameEditText.requestFocus();
            return false;
        }else if (emailEdiText.getText().toString().isEmpty()){
            emailEdiText.setError("please enter your email");
            emailEdiText.requestFocus();
            return false;
        }else if (passwordEditText.getText().toString().isEmpty()){
            passwordEditText.setError("please enter your password");
            passwordEditText.requestFocus();
            return false;
        }
        return true;

    }
}