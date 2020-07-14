package com.zs.util;

import com.zs.bean.Movie;
import com.zs.bean.MyQueue;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindRunnable implements Runnable {
    private MyQueue movieList;
    private Map<String, Integer> keyWords;
    private int start;
    private int end;
    private String type;

    public FindRunnable(MyQueue movieList, Map<String, Integer> keyWords, int start, int end,String type) {
        this.movieList = movieList;
        this.keyWords = keyWords;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    @Override
    public void run() {

        processCommand();

    }
    private void processCommand() {
        try {

            RedisUtils redisUtils=new RedisUtils();

            List<String> mIdList=new ArrayList<>();

            Jedis jedis = redisUtils.getJedis();

            mIdList=jedis.lrange("m_list",start,end);

            for (String mId: mIdList) {

                double grade = redisUtils.getGrade(keyWords, mId);

                if (grade==0){
                    continue;
                }

                Movie tempMovie=redisUtils.getMovie(mId);

                tempMovie.setGrade(grade);

                movieList.add(tempMovie);

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
