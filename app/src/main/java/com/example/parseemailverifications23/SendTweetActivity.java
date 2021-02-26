package com.example.parseemailverifications23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

public class SendTweetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTweet;
    private ListView listView;
    private Button buttonOtherTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        editTextTweet = findViewById(R.id.editTextTweet);
        listView = findViewById(R.id.listView);
        buttonOtherTweets = findViewById(R.id.buttonOtherTweets);
        buttonOtherTweets.setOnClickListener(this);

       /* HashMap<String, Integer> number = new HashMap<>();
        number.put("Number1", 1);
        number.put("Number2", 2);

        FancyToast.makeText(this, number.get("Number1") + "", Toast.LENGTH_SHORT,
                FancyToast.INFO, false).show();
        */
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutItem:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        startActivity(new Intent(SendTweetActivity.this, MainActivity.class));
                        finish();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendTweet(View view) {
        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet", editTextTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(SendTweetActivity.this, ParseUser.getCurrentUser().getUsername() +
                            "'s tweet(" + editTextTweet.getText().toString() + ") is saved", FancyToast.LENGTH_SHORT,
                            FancyToast.INFO, false).show();
                }
                else {
                    FancyToast.makeText(SendTweetActivity.this, e.getMessage().toString(), FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR, false).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}