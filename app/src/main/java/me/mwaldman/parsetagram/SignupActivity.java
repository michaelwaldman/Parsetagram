package me.mwaldman.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
EditText username;
EditText password;
EditText email;
Button SignupButton;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.etUser);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        SignupButton = findViewById(R.id.SignUpBtn);

        final ParseUser user = new ParseUser();
        Toast.makeText(this, "clicked on register", Toast.LENGTH_LONG).show();
// Set core properties

        context=this;

        System.out.println("Username is" + user.getUsername());
        System.out.println("Username is" + username.getText().toString());
        System.out.println("Password is" + password.getText().toString());



// Set custom properties
// Invoke signUpInBackground
        SignupButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Intent i = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(context, "Error signing up", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            System.out.println("Error is " + e.getMessage());
                        }
                    }
                });
            }
        });

    }
}
