package com.codepath.apps.mysimpletweets.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.Activities.TimelineActivity;
import com.codepath.apps.mysimpletweets.Models.Tweet;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.Utils.LinkifiedTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vjobanputra on 9/30/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        LinkifiedTextView tvTweetBody = (LinkifiedTextView) convertView.findViewById(R.id.tvTweetBody);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);

        TextView tvFavorites = (TextView) convertView.findViewById(R.id.tvFavorites);
        TextView tvRetweets = (TextView) convertView.findViewById(R.id.tvRetweets);
        TextView tvReply = (TextView) convertView.findViewById(R.id.tvReply);

        tvFavorites.setText("\u2605 " + tweet.getFavouriteCount());
        tvRetweets.setText("↺ " + tweet.getRetweetCount()); //u+27f2
        tvReply.setText("←");

        tvUsername.setText(Html.fromHtml(tweet.getUser().getDisplayName()));
        tvTweetBody.setText(tweet.getBody());
        tvTweetBody.setMovementMethod(null);
        tvTimestamp.setText(tweet.getCreatedAt());
        ivProfilePic.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfilePic);

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                v.getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
