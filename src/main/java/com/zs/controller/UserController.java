package com.zs.controller;

import com.zs.bean.User;
import com.zs.service.UserService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wltea.analyzer.lucene.IKAnalyzer;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: DengLong
 * Date: 2020-05-04
 * Time: 15:07
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 跳板
    @RequestMapping("/insertUser")
    public String insertUser() throws Exception {

        return "insertUser";
    }

    // 跳板
    @RequestMapping("/index")
    public String index() throws Exception {

        return "index";
    }

    // 插入方法
    @RequestMapping("/doInsertUser")
    public String doInsertUser(User user) {
        user.setId(22);
        userService.insertUser(user);
        return "insertSuccess";
    }
}
