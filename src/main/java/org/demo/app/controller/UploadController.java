package org.demo.app.controller;

import org.demo.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class UploadController implements ActionListener {

    private final JFileChooser fileChooser;
    private JButton openButton;
    private final ArticleService controller;

    @Autowired
    public UploadController(JFileChooser fileChooser,
                            @Qualifier("openFileChooserButton") JButton openButton,
                            ArticleService facade) {
        this.fileChooser = fileChooser;
        this.openButton = openButton;
        this.controller = facade;
    }

    @PostConstruct
    protected void init() {
        openButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            controller.addFiles(fileChooser.getSelectedFiles());
        }
    }
}
