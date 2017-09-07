package org.demo;

import lombok.extern.slf4j.Slf4j;
import org.demo.app.controller.UploadController;
import org.demo.app.service.ArticleService;
import org.demo.app.view.GuiElementsConfig;
import org.demo.solr.SolrRepositoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Slf4j
@Component
@Configuration
@ComponentScan(basePackageClasses = {ArticleService.class,
        GuiElementsConfig.class,
        SolrRepositoryConfig.class,
        UploadController.class,
        AsyncExecutionConfig.class
})
public class Application implements CommandLineRunner {

    private JFrame mainFrame;

    @Autowired
    public Application(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void run(String... args) throws Exception {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication app =  new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setHeadless(false);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
