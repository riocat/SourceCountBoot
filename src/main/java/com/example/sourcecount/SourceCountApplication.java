package com.example.sourcecount;

import com.example.sourcecount.base.MainAcess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SourceCountApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SourceCountApplication.class, args);

        MainAcess.main(args);

        System.exit(0);
    }
}
