package com.kamil.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email_value = email.getText().toString();
            String password_value = password.getText().toString();

            if(email_value.isEmpty()) Toast.makeText(this, "Please, fill the email.", Toast.LENGTH_SHORT).show();
            else if(password_value.isEmpty()) Toast.makeText(this, "Please, fill the password.", Toast.LENGTH_SHORT).show();
            else {
                DBAdapterUser dbAdapterUser = new DBAdapterUser(this).open();
                MainActivity.user = dbAdapterUser.login(email_value, password_value);
                dbAdapterUser.close();
                if(MainActivity.user == null) {
                    Toast.makeText(this, "No such user.", Toast.LENGTH_SHORT).show();
                    return;
                }

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.putLong("user_id", MainActivity.user.id);
                editor.commit();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}