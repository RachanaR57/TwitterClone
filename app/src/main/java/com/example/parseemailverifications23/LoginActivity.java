package com.example.parseemailverifications23;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginUserName, editTextLoginPassword;
    private Button buttonLoginSignUp, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Page");

        editTextLoginUserName = findViewById(R.id.editTextLoginUserName);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        buttonLoginSignUp = findViewById(R.id.buttonLoginsignUp);
        buttonLogin = findViewById(R.id.buttonLogin);

        editTextLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    loginPressed(buttonLogin);
                }
                return false;
            }
        });

        buttonLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    public void loginPressed (View buttonView) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In... ");
        progressDialog.show();
        ParseUser.logInInBackground(editTextLoginUserName.getText().toString(),
                editTextLoginPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null && e == null) {
                    //alertDisplayer("Login Successful", "Welcome" + editTextLoginUserName.getText().toString() + "!", false);
                    startActivity(new Intent(LoginActivity.this, TwitterUsers.class));
                }
                else {
                    ParseUser.logOut();
                    alertDisplayer("Login Fail", e.getMessage() + " Please Retry!", true);
                }
                progressDialog.dismiss();
            }
        });
    }
    private void alertDisplayer(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (!error) {
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
    public void rootLayoutLoginTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}