package org.demo;

import lombok.extern.slf4j.Slf4j;
import org.demo.app.controller.FileController;
import org.demo.app.facade.ArticleCollectionFacade;
import org.demo.app.view.GuiElementsConfig;
import org.demo.conversion.ConversionServiceFactoryBean;
import org.demo.solr.SolrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Slf4j
@Component
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = {ArticleCollectionFacade.class, ConversionServiceFactoryBean.class, GuiElementsConfig.class, SolrConfig.class, FileController.class})
public class AppRunner implements CommandLineRunner {

    private JFrame mainFrame;

    @Autowired
    public AppRunner(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void run(String... args) throws Exception {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication app =  new SpringApplication(AppRunner.class);
        app.setWebEnvironment(false);
        app.setHeadless(false);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
