package me.mwaldman.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import me.mwaldman.parsetagram.model.Post;

public class DetailsActivity extends AppCompatActivity {
    TextView tvDetailsDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView image = findViewById(R.id.ivImage);
        tvDetailsDescription = findViewById(R.id.tvDetailsDescription);
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Glide.with(this).load(post.getImage().getUrl()).into(image);
        tvDetailsDescription.setText(post.getDescription());

    }
}
