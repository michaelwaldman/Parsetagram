package me.mwaldman.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        registerBtn = findViewById(R.id.btnRegister);
        loginBtn = findViewById(R.id.btnLogin);
        context = this;
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

    }
    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null){
                    Log.d("LoginActivity", "Login successful");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                    Toast.makeText(context, "Wrong username/password", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
