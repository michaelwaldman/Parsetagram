package me.mwaldman.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import org.parceler.Parcels;

import me.mwaldman.parsetagram.model.Post;

public class DetailsActivity extends AppCompatActivity {
    TextView tvDetailsDescription;
    TextView tvUserDetails;
    TextView tvTime;
    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView image = findViewById(R.id.ivImage);
        tvDetailsDescription = findViewById(R.id.tvDetailsDescription);
        tvUserDetails = findViewById(R.id.tvUsernameDetails);
        tvTime = findViewById(R.id.tvTime);
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Glide.with(this).load(post.getImage().getUrl()).into(image);
        tvDetailsDescription.setText(post.getDescription());
        tvUserDetails.setText(post.getUser().getUsername());
        tvTime.setText(post.getDate());
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.compose:
                        Intent i = new Intent(DetailsActivity.this, HomeActivity.class);
                        startActivity(i);

                        return true;
                    case R.id.home:
                        Intent i2 = new Intent(DetailsActivity.this, TimelineActivity.class);
                        startActivity(i2);
                        return true;
                    case R.id.logout:
                        ParseUser.logOut();
                        Intent i3 = new Intent(DetailsActivity.this, MainActivity.class);
                        startActivity(i3);
                        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null                        return true;
                }
                return false;
            }
        });          }
}
