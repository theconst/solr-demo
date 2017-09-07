package org.demo.solr.repository;

import org.demo.pojo.Article;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

public interface ArticleRepository extends SolrCrudRepository<Article, String> {

    @Async
    ListenableFuture<List<Article>> findByContent(String queryString); //mind the naming conventions
}
