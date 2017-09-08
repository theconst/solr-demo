package org.demo.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.demo.app.service.ArticleService;
import org.demo.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;

@Component
@Slf4j
public class SearchController implements ActionListener {

    private final JTextField searchField;
    private final JTextArea resultsArea;
    private final JButton searchButton;

    private final ArticleService facade;

    @Autowired
    public SearchController(JTextField searchField,
                            @Qualifier("searchSubmitButton") JButton searchButton,
                            JTextArea resultsArea,
                            ArticleService service) {
        this.searchField = searchField;
        this.searchButton = searchButton;
        this.facade = service;
        this.resultsArea = resultsArea;
    }

    @PostConstruct
    protected void init() {
        searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String text = searchField.getText();
        facade.searchForArticles(
                articles -> {
                    log.info(format("Successfully searched for %s", text));
                    resultsArea.setText(stream(articles.spliterator(), false)
                        .map(this::prettyPrintArticle)
                        .collect(joining("\n\n")));
                },
                ex -> {
                    log.error("Error during searchForArticles", ex);
                    resultsArea.setText("ERROR");
                },
                text);
    }

    private String prettyPrintArticle(Article article) {
        return format("Title:%s%nContents:%n%s%n", article.getTitle(), article.getContent());
    }
}
