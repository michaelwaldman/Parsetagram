package me.mwaldman.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.mwaldman.parsetagram.model.Post;

public class TimelineActivity extends AppCompatActivity {
    Context context;
    RecyclerView rvPost;
    SwipeRefreshLayout swipeContainer;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvTimeline;
    TextView tvTime;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        context = getApplicationContext();
        tvTime = findViewById(R.id.tvTime);
       // rvTimeline = findViewById(R.id.rvTimeline);
        rvPost = findViewById(R.id.rvTimeline);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvPost.setLayoutManager(new LinearLayoutManager(context));
        rvPost.setAdapter(postAdapter);
        loadTopPosts();
        // Only ever call `setContentView` once right at the top
        // Lookup the swipe conmwaainer view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.compose:
                        Intent i = new Intent(TimelineActivity.this, HomeActivity.class);
                        startActivity(i);

                        return true;
                    case R.id.home:
                        Intent i2 = new Intent(TimelineActivity.this, TimelineActivity.class);
                        startActivity(i2);
                        return true;
                    case R.id.logout:
                        ParseUser.logOut();
                        Intent i3 = new Intent(TimelineActivity.this, MainActivity.class);
                        startActivity(i3);
                        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null                        return true;
                }
                return false;
            }
        });          }


    public void fetchTimelineAsync(int page) {
        final ArrayList<Post> newPosts;
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        final Post.Query postQuery = new Post.Query();
        postQuery.addDescendingOrder("createdAt");

        postQuery.getTop().withUser();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null){

                    //// Remember to CLEAR OUT old items before appending in the new ones
                        postAdapter.clear();
                    postAdapter.addAll(objects);

                    //                // ...the data has come back, add new items to your adapter...
                    //                // Now we call setRefreshing(false) to signal refresh has finished
                               swipeContainer.setRefreshing(false);
                }
                else{
                    e.printStackTrace();
                }
            }
        });}

    private void loadTopPosts(){
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.addDescendingOrder("createdAt");
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null){
                    for(int i = 0; i<objects.size(); ++i) {
                        Log.d("HomeActivity", "Post["+i+"] = "
                                + objects.get(i).getDescription()
                                +"\n username = " + objects.get(i).getUser().getUsername());
                    }
                    postAdapter.addAll(objects);
                    //postAdapter.notifyDataSetChanged();
                }
                else{
                    e.printStackTrace();
                }
            }
        });}
}
