// Time Complexity : O(1) for follow, unfollow, post tweet methods | for feeds, TC = O(nLogk) where n is total tweets of users it follows and k is 10
// Space Complexity : O(n) for hashmaps, hashset, arraylist used. O(k) for priority queue i.e O(1)
// Did this code successfully run on Leetcode : yes

package com.madhurima;

import java.util.*;

public class DesignTwitter {
}

class Twitter {

    class Tweet{
        int tweetId;
        int timeStamp;
        public Tweet(int tweetId, int timeStamp){
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> allUsers = userMap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
        for(int user: allUsers){
            List<Tweet> allTweets = tweetMap.get(user);
            if(allTweets != null){
                for(Tweet tw: allTweets){
                    pq.add(tw);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }

        List<Integer> answer = new ArrayList<>();
        while(!pq.isEmpty()){
            answer.add(0, pq.poll().tweetId);
        }

        return answer;

    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId) && followerId != followeeId){
            if(userMap.get(followerId).contains(followeeId)){
                userMap.get(followerId).remove(followeeId);
            }
        }
    }
}
