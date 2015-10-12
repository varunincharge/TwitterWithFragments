package com.codepath.apps.mysimpletweets.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.Fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.Models.User;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.Utils.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();

        String screenName = getIntent().getStringExtra("screen_name");
        client .getUserInfo(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = User.fromJson(response);
                getSupportActionBar().setTitle("@" + user.getScreenName());
                populateProfileHeader(user);
            }
        });

        if (savedInstanceState == null) {
            UserTimelineFragment utlFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, utlFragment);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        ImageView ivProfilePic = (ImageView)findViewById(R.id.ivProfilePic);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvNumTweets = (TextView) findViewById(R.id.tvNumTweets);
        TextView tvNumFollowing = (TextView) findViewById(R.id.tvNumFollowing);
        TextView tvNumFollowers = (TextView) findViewById(R.id.tvNumFollowers);

        String uname = "<B>" + "@" + user.getScreenName() + " </B>";
        tvScreenName.setText(Html.fromHtml(uname));
        tvDescription.setText(user.getDescription());
        tvNumTweets.setText(user.getNumTweets() + " Tweets");
        tvNumFollowers.setText(user.getNumFollowers() + " Followers");
        tvNumFollowing.setText(user.getNumFollowing() + " Following");

        Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).into(ivProfilePic);


    }
}
