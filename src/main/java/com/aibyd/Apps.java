package com.aibyd;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created with VSCODE
 * User: Arven
 * Date: 2018/9/28
 * Time: 15:45
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Apps {
    public static void main(String[] args) {
        SpringApplication.run(Apps.class, args);
    }
}