package org.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Configuration
@EnableAsync
public class AsyncExecutionConfig {

    @Bean("taskExecutor")
    public Executor executor() {
        return newFixedThreadPool(20);
    }
}
