package org.demo.app.service;

import lombok.extern.slf4j.Slf4j;
import org.demo.pojo.Article;
import org.demo.solr.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

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
abstract public class ArticleService {

    private static final Charset CHARSET = Charset.defaultCharset();

    private final ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository solrService) {
        this.repository = solrService;
    }

    @Async
    public void addArticleFiles(SuccessCallback<? super Article> successCallback,
                                FailureCallback failureCallback, File... files) {
        List<Article> articles = new ArrayList<>(files.length);
        for (File file : files) {
            if (file.isDirectory()) {
                self().addArticleFiles(successCallback, failureCallback, file.listFiles());
            } else {
                try {
                    articles.add(Article.builder()
                            .content(readFileToString(file, CHARSET))
                            .fileName(file.getName())
                            .title(file.getName())
                            .build());
                } catch (IOException ex) {
                    failureCallback.onFailure(ex);
                    log.info("Error reading file", ex);
                }
            }
        }
        try {
            repository.saveAll(articles);
            articles.forEach(successCallback::onSuccess);
            log.info(format("Files %s were successfully added", Arrays.toString(files)));
        } catch (Exception ex) {
            failureCallback.onFailure(ex);
            log.info(format("Failed to submit files: %s", files));
        }
    }

    public void searchForArticles(SuccessCallback<Iterable<Article>> successCallback, FailureCallback failureCallback, String queryString) {
        repository.findByContent(queryString)
                .addCallback(successCallback, failureCallback);
    }

    @Lookup
    abstract protected ArticleService self();
}
