package com.smart.security.app.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arnold.zhao <a href="mailto:13949123615@163.com"/>
 * @create 2019-03-31
 */
@RestController
public class TestController {

    @RequestMapping("/abc")
    public String abc() {

        return "SSSSS";
    }
}
