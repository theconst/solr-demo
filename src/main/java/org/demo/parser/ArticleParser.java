package org.demo.parser;

import org.demo.pojo.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class ArticleParser {

    public Article parse(String html) {
        Document articleDocument = Jsoup.parse(html);

        String title = articleDocument.getElementsByTag("h1").stream()
                        .findFirst()
                        .map(Element::html)
                        .orElse("untitled");

        String content = Optional.of(articleDocument.getElementById("bodyContent"))
                .map(Element::html)
                .orElse(articleDocument.html());

        List<String> categories = articleDocument.getElementsByClass("catlinks").stream()
                    .findFirst()
                    .map(element -> element.getElementsByTag("span").stream()
                        .map(x -> x.getElementsByTag("a").stream()
                                .findFirst()
                                .map(Element::text)
                                .orElse(""))
                        .collect(toList()))
                .orElse(emptyList());

        return Article.builder()
                .content(content)
                .title(title)
                .categories(categories)
                .build();
    }
}
