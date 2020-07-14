package com.zs.controller;

import com.zs.bean.Movie;
import com.zs.bean.MyQueue;
import com.zs.bean.User;
import com.zs.service.DataService;
import com.zs.service.UserService;
import com.zs.util.FindRunnable;
import com.zs.util.LuceneUtils;
import com.zs.util.RedisUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: DengLong
 * Date: 2020-05-04
 * Time: 15:07
 */
@Controller
@RequestMapping("/find")
@CrossOrigin    //允许跨域
public class FindController {
    @Autowired
    private UserService userService;

    @Autowired
    private DataService dataService;

    private static final int CORE_POOL_SIZE = 20;
    private static final int MAX_POOL_SIZE = 30;
    private static final int QUEUE_CAPACITY = 200;
    private static final Long KEEP_ALIVE_TIME = 1L;

    // 跳板
    @RequestMapping("/find")
    @ResponseBody
    public User toFind(String s){

        System.out.println(s);

        User user=new User(111,"张三","aaaaa");
        return user;
    }

    @RequestMapping("/getMovie")
    @ResponseBody   //返回值转json
    public String getMovie(String name, String password, String rem) throws Exception {

        return name+"/"+rem;
    }

//    @RequestMapping(value = "plans",method = RequestMethod.POST,consumes = "application/json")
//    @ResponseBody
//    public String addMyPlans(@RequestBody String plans){
//
//        System.out.println(plans);
//        List<User> list=new ArrayList<>();
//        User user1=new User(111,"张三","aaaaa");
//        User user2=new User(111,"张三","aaaaa");
//        list.add(user1);
//        list.add(user2);
//
//        JSONArray jsonArray=new JSONArray(list);
//
//        return jsonArray.toString();
//    }

    @RequestMapping(value = "doFind",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String doFind(@RequestBody String data) throws Exception {

        RedisUtils redisUtils=new RedisUtils();

        Jedis jedis=new RedisUtils().getJedis();

        MyQueue movieList=new MyQueue();

        List<Movie> resultList=null;

        JSONObject json = new JSONObject(data);

        String words=json.getString("keywords");
        String tags=json.getString("tag");
        String[] tag=tags.split("/");

        String tempKey=words+tags;

        System.out.println(tags);

        // 判断是否近期查询过，如果没查过，再按照查询形式查询
        if(!jedis.exists(tempKey)){

            long dataNum=0;
            String type="";

            if(tags.equals("-1/-1/-1")){
                //快速查询
                dataNum=jedis.llen("m_list");
                type="m_list";
            }
            else {
                //分类查询
                type="set"+tag[0]+"-"+tag[1]+"-"+tag[2];
                dataNum=jedis.smembers(type).size();
            }


            Map<String, Integer> keyWords = new LuceneUtils().getFrequency(words);

            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAX_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                    new ThreadPoolExecutor.CallerRunsPolicy());

            long WorkNum=dataNum/12+1;
            int threadWorkNum=(int) WorkNum;


            for (int i = 0; i <= 12; i++) {
                //创建WorkerThread对象（WorkerThread类实现了Runnable 接⼝）
                Runnable worker;

                worker = new FindRunnable(movieList,keyWords,i*threadWorkNum,(i+1)*threadWorkNum,type);

                //执⾏Runnable
                executor.execute(worker);
            }

            //终⽌线程池
            executor.shutdown();

            while (!executor.isTerminated()) {
            }
            System.out.println("Finished all threads");

            resultList=movieList.list;

            System.out.println(movieList.toString());

            // 将查询结果插入缓存
            for (Movie movie: movieList.list) {

                jedis.rpush(tempKey,movie.getmId());

            }

            jedis.expire(tempKey, 120);

        }else {
            // 若该结果在两分钟内查询过，就从缓存里取出，直接发给前端

            List<Movie> tempList=new ArrayList<>();
            List<String> mIdList = jedis.lrange(tempKey, 0, -1);
            for (String mId: mIdList) {

                tempList.add(redisUtils.getMovie(mId));

            }
            resultList=tempList;
            System.out.println("使用缓存中的查询结果。");
        }

        JSONArray jsonArray=new JSONArray(resultList);

        String jsonResult="{\"num\":"+resultList.size()+",\"data\":"+jsonArray.toString()+"}";

//        return jsonArray.toString();
        return jsonResult;
    }


    @RequestMapping("/doProcessed")
    public String doProcessed() throws Exception {

//        System.out.println(userService.getMovie("30227798"));

        return "find";
    }

    // 跳板
    @RequestMapping("/index")
    public String toIndex() throws Exception {

        return "index";
    }

    // 跳板
    @RequestMapping("/details")
    public String toDetails() throws Exception {

        return "details";
    }

    // 插入方法
    @RequestMapping("/doInsertUser")
    public String doInsertUser(User user) {
        user.setId(22);
        userService.insertUser(user);
        return "insertSuccess";
    }

    // 自动开启首页
    @Configuration
    public class IndexConfig{
        @EventListener({ApplicationReadyEvent.class})
        void applicationReadyEvent() {
            System.out.println("应用已经准备就绪 ... 启动浏览器");
            String url = "http://localhost:8181/html/index.html";
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
