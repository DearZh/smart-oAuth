package com.smart.security.example.platform.users.controller;

import com.smart.security.example.platform.users.bean.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-29
 */
@RestController
public class UserController {

    //强撸灰飞烟灭
    @RequestMapping("/user")
    public List<User> query() {

        List<User> users = new ArrayList<>();
        users.add(new User());

        return users;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User queryId(@PathVariable(name = "id") String id) {

        return null;
    }
}
