package org.demo.app.controller;

import org.demo.app.facade.ArticleCollectionFacade;
import org.demo.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class SearchController implements ActionListener {

    private final JTextField searchField;

    private final JButton searchButton;
    private final ArticleCollectionFacade facade;

    private final JTextArea resultsArea;
    private final String collectionName;

    @Autowired
    public SearchController(JTextField searchField,
                            @Qualifier("searchSubmitButton") JButton searchButton,
                            JTextArea resultsArea,
                            ArticleCollectionFacade facade,
                            @Value("${collection.name?:wikipedia}") String collectionName) {
        this.searchField = searchField;
        this.searchButton = searchButton;
        this.facade = facade;
        this.resultsArea = resultsArea;
        this.collectionName = collectionName;
    }

    @PostConstruct
    protected void init() {
        searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = searchField.getText();
        List<Article> result = facade.searchForBeansInCollection(collectionName, text, Article.class);

        resultsArea.setText("Found in article:"
                + result.stream()
                .findFirst()
                .map(Article::getFileName)
                .orElse(""));
    }
}
