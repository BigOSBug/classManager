package com.yanzhen;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.yanzhen.mapper")
class TeamProjectApplicationTests {

//    @Autowired
//    private IUserService userService;
    @Test
    void contextLoads() {
//
//        //新增
//        UserInfo info=new UserInfo();
//        info.setUsername("admins");
//        info.setPassword("123");
//        boolean bs=userService.save(info);
//        if(bs){
//            System.out.println("成功");
//        }else{
//            System.out.println("失败");
//        }

    }

}
