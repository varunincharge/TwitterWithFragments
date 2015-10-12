package com.codepath.apps.mysimpletweets.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.Fragments.HomeFragment;
import com.codepath.apps.mysimpletweets.Fragments.MentionsFragment;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.Utils.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.Utils.TwitterClient;
import com.codepath.apps.mysimpletweets.Models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener, SearchDialog.SearchDialogListener{

//    private final int REQUEST_CODE_COMPOSE = 20;
//    private final int REQUEST_CODE_DETAIL = 30;

    ViewPager vpPager;
    PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        getSupportActionBar().setTitle("Home"); // Couldn't get this to work :-(

        vpPager = (ViewPager) findViewById(R.id.viewpager);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        tabStrip.setViewPager(vpPager);

    }

//    private void populateTimeLineOffline() {
//        List<Tweet> queryResults = new Select().from(Tweet.class).execute();
//        aTweets.addAll(queryResults);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miCompose) {
            FragmentManager fm = getSupportFragmentManager();
            ComposeTweetDialog composeTweetDialog = ComposeTweetDialog.newInstance("Tweet");
            composeTweetDialog.show(fm, "activity_compose");
        } else if (id == R.id.miSearch) {
            FragmentManager fm = getSupportFragmentManager();
            SearchDialog searchDialog = SearchDialog.newInstance("");
            searchDialog.show(fm, "activity_search");
        } else if (id == R.id.miProfile) {
             Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
             startActivityForResult(i, 100 /*REQUEST_CODE_DETAIL*/);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishComposeTweetDialog(Tweet t) {
//        if (t != null) {
//            tweets.add(0, t);
//            aTweets.notifyDataSetChanged();
//        }
    }

    @Override
    public void onFinishSearchDialog(String s) {
//        if (s != null) {
//            client.getSearch(s, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    JSONArray tweetsArray = null;
//                    try {
//                        tweetsArray = response.getJSONArray("statuses");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    ArrayList tw = Tweet.fromJsonArray(tweetsArray);
//                    aTweets.clear();
//                    aTweets.addAll(tw);
//                    swipeContainer.setRefreshing(false);
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    swipeContainer.setRefreshing(false);
//                    Log.d("DEBUG", errorResponse.toString());
//                }
//            });
//
//        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        private String tabs[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public String getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeFragment();
            } else if (position == 1) {
                return new MentionsFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabs.length;
        }
    }
}