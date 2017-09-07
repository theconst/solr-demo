package org.demo.app.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


@Configuration
@ComponentScan({"org.demo.app.controller", "org.demo.app.view"})
public class GuiElementsConfig {

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
        fileChooser.setFileSelectionMode(FILES_AND_DIRECTORIES);

        return fileChooser;
    }

    @Bean("searchField")
    public JTextField searchField(GuiConfigurationProperties props) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(props.getSearchFieldWidth(), props.getSearchFieldHeight()));

        return textField;
    }

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

    @Bean("searchScrollPane")
    public JScrollPane searchResultsScrollPane(@Qualifier("searchResultsTextArea") JTextArea searchTextArea,
                                               GuiConfigurationProperties properties) {
        JScrollPane pane = new JScrollPane (searchTextArea, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setPreferredSize(new Dimension(properties.getResultAreaWidth(), properties.getResultAreaHeight()));
        pane.setWheelScrollingEnabled(true);

        return pane;
    }

    @Bean("flowLayout")
    public LayoutManager flowLayout() {
        return new FlowLayout();
    }

}
