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
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextUserName, editTextPassword;
    private Button buttonSignUpLogin, buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up Page");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonSignUpLogin = findViewById(R.id.buttonSignUpLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    signUpPressed(buttonSignUp);
                }
                return false;
            }
        });

        buttonSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    public void signUpPressed (View buttonView) {
//        Toast.makeText(this, "Sign up pressed", Toast.LENGTH_SHORT).show();
        try {
            ParseUser user = new ParseUser();
            user.setUsername(editTextUserName.getText().toString());
            user.setPassword(editTextPassword.getText().toString());
            user.setEmail(editTextEmail.getText().toString());

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing Up " + editTextUserName.getText().toString());
            progressDialog.show();

            user.signUpInBackground(new SignUpCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser.logOut();
                        alertDisplayer("Account Created Successfully!", "You can now login!" , false);
                    }
                    else {
                        ParseUser.logOut();
                        alertDisplayer("Error: Account Creation Failed", "Account could not be created:" + e.getMessage(), true);
                    }
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void alertDisplayer(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (!error) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}