package org.demo.parser;

import org.demo.pojo.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class ArticleParser {

    public Article parse(String html) {
        Document articleDocument = Jsoup.parse(html);

        String title = articleDocument.getElementsByTag("h1").stream()
                .findFirst()
                .map(Element::html)
                .orElse("untitled");

        String content = Optional.ofNullable(articleDocument.getElementById("bodyContent"))
                .map(Element::html)
                .orElse(articleDocument.html());

        List<String> categories = articleDocument.getElementsByClass("catlinks").stream()
                .findFirst()
                .map(element -> element.getElementsByTag("span").stream()
                        .map(x -> x.getElementsByTag("a").stream()
                                .findFirst()
                                .map(Element::text)
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .collect(toList()))
                .orElse(null);

        return Article.builder()
                .title(title)
                .content(content)
                .categories(categories)
                .build();
    }
}
