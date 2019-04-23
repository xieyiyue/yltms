package com.haoche.yltms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*启动时不检查数据库连接
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)*/
@SpringBootApplication
@EnableJpaRepositories
public class YltmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(YltmsApplication.class, args);
        System.out.println("--------------------启动完成---------------------");
    }


}
