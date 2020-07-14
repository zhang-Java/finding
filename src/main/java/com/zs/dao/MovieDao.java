package com.zs.dao;

import com.zs.util.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class MovieDao {

    public Map<String,String> getMovie(String mId){
        Jedis jedis = new RedisUtils().getJedis();

        Map<String, String> map=jedis.hgetAll("30227798");
//        System.out.println(map);
        jedis.close();

        return map;
    }


}
