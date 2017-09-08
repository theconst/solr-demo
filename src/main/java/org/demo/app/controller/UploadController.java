package org.demo.app.controller;

import org.demo.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.String.format;

@Component
public class UploadController implements ActionListener {

    private final JFileChooser fileChooser;
    private final JButton openButton;
    private final ArticleService controller;
    private final JLabel statusBar;

    @Autowired
    public UploadController(JFileChooser fileChooser,
                            @Qualifier("openFileChooserButton") JButton openButton,
                            @Qualifier("statusBar") JLabel statusBar,
                            ArticleService facade) {
        this.fileChooser = fileChooser;
        this.openButton = openButton;
        this.controller = facade;
        this.statusBar = statusBar;
    }

    @PostConstruct
    protected void init() {
        openButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            controller.addArticleFiles(
                    article -> statusBar.setText(format("Article %s was successfully submitted", article.getTitle())),
                    ex -> statusBar.setText(format("Error! %s", ex.getMessage())),
                    fileChooser.getSelectedFiles());
        }
    }
}
