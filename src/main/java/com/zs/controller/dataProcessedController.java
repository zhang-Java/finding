package com.zs.controller;

import com.zs.bean.Movie;
import com.zs.bean.User;
import com.zs.service.DataService;
import com.zs.util.LuceneUtils;
import com.zs.util.RedisUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: DengLong
 * Date: 2020-05-04
 * Time: 15:07
 */
@Controller
@RequestMapping("/processed")
@CrossOrigin    //允许跨域
public class dataProcessedController {
    @Autowired
    private DataService dataService;

    // 跳板
    @RequestMapping("/find")
    @ResponseBody   //返回值转json
    public Movie toFind() throws Exception {

//        RedisUtils redisUtils=new RedisUtils();
//
//        List<String> movieId = redisUtils.getMovieId();
//
//        for (String mId: movieId) {
//
//            redisUtils.indexOptimization(mId);
//
//        }

//        redisUtils.setTypeList();

        return new RedisUtils().getMovie("33401841");
    }

    @RequestMapping("/hotWordProcessed")
    public String HotWordProcessed() {

        List<String> mIdList=new RedisUtils().getMovieId();

        for (String mId: mIdList) {

            List<String> names = new RedisUtils().getNames(mId);

            for (String name: names) {

                if ((new RedisUtils().setHotWord(name))!=0){
                    new RedisUtils().writeHotWord(name);
                }

            }

        }

        System.out.println(mIdList.size());

        return "index";

    }



}
