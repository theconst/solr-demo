package org.demo.app.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


//TODO: add centralized configuration
@Configuration
@ComponentScan({"org.demo.app.controller", "org.demo.app.view"})
public class GuiElementsConfig {

    @Autowired
    @Bean("openFileChooserButton")
    public JButton openFileChooserButton(GuiConfigurationProperties properties) {
        JButton openFileButton = new JButton(properties.getChooseFileButtonText());

        return openFileButton;
    }

    @Bean("fileChooser")
    public JFileChooser fileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setMultiSelectionEnabled(true);
        return fileChooser;
    }

    @Bean("searchField")
    public JTextField searchField() {
        JTextField textField = new JTextField();
        textField.setEnabled(true);
        textField.setEditable(true);

        return textField;
    }

    @Autowired
    @Bean("searchSubmitButton")
    public JButton searchSubmitButton(GuiConfigurationProperties props) {
        JButton button = new JButton(props.getSearchButtonText());

        return button;
    }

    @Bean("searchResultsTextArea")
    public JTextArea searchResultsTextArea() {
        JTextArea textArea = new JTextArea();

        return textArea;
    }

    @Autowired
    @Bean("searchScrollPane")
    public JScrollPane searchResultsScrollPane(@Qualifier("searchResultsTextArea") JTextArea searchTextArea) {
        JScrollPane pane = new JScrollPane (searchTextArea, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);

        pane.setPreferredSize(new Dimension(300, 300));
        pane.setWheelScrollingEnabled(true);

        return pane;
    }

    @Bean("flowLayout")
    public LayoutManager flowLayout() {
        return new FlowLayout();
    }

    @Autowired
    @Bean("mainFrame")
    public JFrame mainFrame(@Qualifier("flowLayout") LayoutManager layout,
                            @Qualifier("searchScrollPane") JScrollPane searchResultsPane,
                            @Qualifier("searchField") JTextField searchBox,
                            @Qualifier("searchSubmitButton") JButton searchButton,
                            @Qualifier("openFileChooserButton") JButton fileChooserButton,
                            GuiConfigurationProperties properties) {
        JFrame mainFrame = new JFrame(properties.getName());
        mainFrame.setLayout(layout);

        //TODO: add more fancy stuff
        mainFrame.add(searchBox);
        searchBox.setColumns(5);

        mainFrame.add(searchButton);
        mainFrame.add(fileChooserButton);
        mainFrame.add(searchResultsPane);

        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();

        return mainFrame;
    }
}
