package com.codepath.apps.mysimpletweets.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.Models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vjobanputra on 10/10/15.
 */
public class UserTimelineFragment extends TweetsListFragment  {

    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment f = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeLine(false, null, null);
    }

    @Override
    public void populateTimeLine(final boolean addFirst, final String since_id, String max_id) {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, since_id, max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList tw = Tweet.fromJsonArray(response);
                if (addFirst) {
                    tweets.addAll(0, tw);
                } else {
                    tweets.addAll(tw);
                }
                aTweets.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                swipeContainer.setRefreshing(false);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
