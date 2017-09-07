package org.demo.app.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component("mainFrame")
public class MainFrame extends JFrame {

    private final LayoutManager layout;
    private final JScrollPane searchResultsPane;
    private final JTextField searchBox;
    private final JButton searchButton;
    private final JButton fileChooserButton;
    private final GuiConfigurationProperties properties;

    public MainFrame(@Qualifier("flowLayout") LayoutManager layout,
                     @Qualifier("searchScrollPane") JScrollPane searchResultsPane,
                     @Qualifier("searchField") JTextField searchBox,
                     @Qualifier("searchSubmitButton") JButton searchButton,
                     @Qualifier("openFileChooserButton") JButton fileChooserButton,
                     GuiConfigurationProperties properties) {
        this.layout = layout;
        this.searchResultsPane = searchResultsPane;
        this.searchBox = searchBox;
        this.searchButton = searchButton;
        this.fileChooserButton = fileChooserButton;
        this.properties = properties;
    }

    //TODO: add more fancy stuff
    @PostConstruct
    public void init() {
        setLayout(layout);
        add(searchBox);
        add(searchButton);
        add(fileChooserButton);
        add(searchResultsPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        pack();
    }

}
