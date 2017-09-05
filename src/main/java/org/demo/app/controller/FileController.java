package org.demo.app.controller;

import org.demo.app.facade.ArticleCollectionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Component
public class FileController implements ActionListener {

    private final JFileChooser fileChooser;
    private JButton openButton;
    private final ArticleCollectionFacade controller;
    private String collectionName;

    @Autowired
    public FileController(JFileChooser fileChooser,
                          @Qualifier("openFileChooserButton") JButton openButton,
                          ArticleCollectionFacade facade,
                          @Value("${collection.name?:wikipedia}") String collectionName) {
        this.fileChooser = fileChooser;
        this.openButton = openButton;
        this.controller = facade;
        this.collectionName = collectionName;
    }

    @PostConstruct
    protected void init() {
        openButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            controller.addFilesToCollection(collectionName, files);
        }
    }
}
