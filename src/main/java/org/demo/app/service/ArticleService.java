package org.demo.app.service;

import lombok.extern.slf4j.Slf4j;
import org.demo.pojo.Article;
import org.demo.solr.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.io.FileUtils.readFileToString;

@Component
@Slf4j
public class ArticleService {

    private static final Charset CHARSET = Charset.defaultCharset();

    private final ArticleRepository repostitory;

    @Autowired
    public ArticleService(ArticleRepository solrService) {
        this.repostitory = solrService;
    }

    @Async
    public void addFiles(File... files) {
        List<Article> articles = new ArrayList<>(files.length);
        for (File file : files) {
            if (file.isDirectory()) {
                getService().addFiles(file.listFiles());     //todo: refactor to loop
            } else {
                try {
                    articles.add(Article.builder()
                            .content(readFileToString(file, CHARSET))
                            .fileName(file.getName())
                            .title(file.getName())
                            .build());
                } catch (IOException ex) {
                    log.info("Error reading file", ex);
                }
            }
        }
        try {
            repostitory.saveAll(articles);
            log.info(format("Files %s were successfully added", Arrays.toString(files)));
        } catch (Exception ex) {
            log.info(format("Failed to submit files: %s", files));
        }
    }

    public ListenableFuture<? extends Iterable<Article>> searchForArticles(String queryString) {
        return repostitory.findByContent(queryString);
    }

    @Lookup
    public ArticleService getService() {
        return null;
    }
}
