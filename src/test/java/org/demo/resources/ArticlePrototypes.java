package org.demo.resources;

import org.demo.pojo.Article;

import java.time.LocalDateTime;

import static java.util.Arrays.asList;

public class ArticlePrototypes extends Article {

    private ArticlePrototypes() {

    }

    public static Article anArticle() {
        return anArticle("fname");
    }

    public static Article anArticle(String fileName) {
        return Article.builder()
                .fileName(fileName)
                .title("title")
                .categories(asList("uncategorized"))
                .createdAt(LocalDateTime.now())
                .content("content")
                .build();
    }

}
