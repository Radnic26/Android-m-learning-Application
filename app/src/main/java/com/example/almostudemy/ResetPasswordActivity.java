package com.example.almostudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText etResetUsername;
    EditText etResetPass;
    EditText etConfirmResetPass;
    Button btnReset;
    public DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        db = new DatabaseHelper(this);
        etResetUsername = findViewById(R.id.etResetUsername);
        etResetPass = findViewById(R.id.etResetPassword);
        etConfirmResetPass = findViewById(R.id.etResetConfirmPassword);
        btnReset = findViewById(R.id.btnResetPass);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = etResetPass.getText().toString().trim();
                String confirmNewPass = etConfirmResetPass.getText().toString().trim();

                String username = etResetUsername.getText().toString().trim();

                if(newPass.equals(confirmNewPass)) {
                    int update = db.resetPassword(username, newPass);

                    if(update > 0) {
                        Toast.makeText(ResetPasswordActivity.this, "Password Reset Successful", Toast.LENGTH_SHORT).show();
                        Intent resetToLogin = new Intent(ResetPasswordActivity.this, MainActivity.class);
                        startActivity(resetToLogin);
                        finish();
                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, "Error Resetting the Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
