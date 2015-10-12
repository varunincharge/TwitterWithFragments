package com.codepath.apps.mysimpletweets.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.Activities.TimelineActivity;
import com.codepath.apps.mysimpletweets.Activities.TweetDetailActivity;
import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.Models.Tweet;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.Utils.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.Utils.TwitterClient;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    private ListView lvTweets;
    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected SwipeRefreshLayout swipeContainer;

    protected TwitterClient client;

    private int REQUEST_CODE_DETAIL = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        setupListeners();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient();
        super.onCreate(savedInstanceState);
    }

    /****************************
     *** Setup listeners
     ****************************/
    private void setupListeners() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                long lastTweetId = aTweets.getItem(totalItemsCount - 1).getTweetId();
                populateTimeLine(false, null, Long.toString(lastTweetId));
                return true;
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent i = new Intent(getActivity(), TweetDetailActivity.class);
                i.putExtra("tweet", tweets.get(pos));
                startActivityForResult(i, REQUEST_CODE_DETAIL);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                long firstTweetId = aTweets.getItem(0).getTweetId();
                populateTimeLine(true, Long.toString(firstTweetId), null);
            }
        });
    }

    public abstract void populateTimeLine(final boolean addFirst, final String since_id, String max_id);

}
