package me.mwaldman.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import me.mwaldman.parsetagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public List<Post> mPosts;
    public Context context;
    public PostAdapter(List<Post> posts){
        mPosts = posts;

    }
    //foreach row, inflate the layout and pass cache references into viewholder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = mPosts.get(position);
        //populate the views according to this data
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());
        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPost);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class );
                i.putExtra("post", Parcels.wrap(post));
                context.startActivity(i);
            }
        });
    }

    //bind gthe values based on pos of the elements

    //create ViewHolder class

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPost;
        public TextView tvDescription;
        public TextView tvUsername;
        public TextView tvRelativeTime;

        public ViewHolder(View itemView) {
            super(itemView);
            //perform findViewById lookups
            ivPost = (ImageView) itemView.findViewById(R.id.ivPost);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);


        }

    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}