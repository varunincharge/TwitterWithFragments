package com.codepath.apps.mysimpletweets.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Table(name = "UserNew")
public class User extends Model implements Serializable {
    @Column(name = "userId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    private String description;
    private int numFollowers;
    private int numFollowing;
    private int numTweets;

    public int getNumTweets() {
        return numTweets;
    }

    public String getName() {
        return name;
    }

    public long getUserId() {
        return userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }


    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.userId = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.description = json.getString("description");
            user.numFollowers = json.getInt("followers_count");
            user.numFollowing = json.getInt("friends_count");
            user.numTweets = json.getInt("statuses_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.save();

        return user;
    }

    public String getDisplayName() {
        return "<B>" + getName() + "</B>" + " @" + getScreenName();
    }

}
