package com.zs.service.impl;

import com.zs.bean.User;
import com.zs.dao.MovieDao;
import com.zs.mapper.UserMapper;
import com.zs.service.DataService;
import com.zs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: DengLong
 * Date: 2020-05-04
 * Time: 14:49
 */
@Service
public class DataServiceImpl implements DataService {

    private MovieDao movieDao=new MovieDao();

    @Override
    public Map<String,String> getMovie(String id) {
        return movieDao.getMovie(id);
    }
}
