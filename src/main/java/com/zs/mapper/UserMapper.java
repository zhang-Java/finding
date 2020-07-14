package com.zs.mapper;

import com.zs.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: DengLong
 * Date: 2020-05-04
 * Time: 14:47
 */
@Repository
@Mapper
public interface UserMapper {
    void insertUser(User user);
}
