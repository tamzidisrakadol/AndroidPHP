package com.example.androiddatabasephp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button logInBtn;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(this,profileActivity.class);
            startActivity(intent);
            return;
        }

        usernameEditText = findViewById(R.id.userLoginEditText);
        passwordEditText=findViewById(R.id.passloginEditText);
        logInBtn=findViewById(R.id.loginBtn);
        signUpText = findViewById(R.id.signUpText);

        logInBtn.setOnClickListener(v -> {
           if (isdataValid()){
               logInUser();
           }

        });
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this,MainActivity.class);
            startActivity(intent);
        });

    }

    private void logInUser() {
        String username = usernameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")){
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                jsonObject.getInt("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email"));
                        Toast.makeText(loginActivity.this, "user login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this,profileActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(loginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",username);
                map.put("password",pass);
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
        }else if (passwordEditText.getText().toString().isEmpty()){
            passwordEditText.setError("Please Enter Your Password");
            passwordEditText.requestFocus();
            return false;
        }
        return true;
    }
}