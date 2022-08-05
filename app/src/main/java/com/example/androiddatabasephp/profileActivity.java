package com.example.androiddatabasephp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class profileActivity extends AppCompatActivity {
    private TextView profileUserName,profileUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Intent intent = new Intent(profileActivity.this,loginActivity.class);
            startActivity(intent);
            return;
        }

        profileUserName=findViewById(R.id.ProfileUserName);
        profileUserEmail=findViewById(R.id.profileUserEmail);

        profileUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        profileUserName.setText(SharedPrefManager.getInstance(this).getUserName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            logOutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOutUser() {
        SharedPrefManager.getInstance(this).logOut();
        Intent intent = new Intent(profileActivity.this,loginActivity.class);
        startActivity(intent);
        finish();
    }
}