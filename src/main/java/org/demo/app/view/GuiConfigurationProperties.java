package org.demo.app.view;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class GuiConfigurationProperties {

    @Value("${app.gui.name:wiki-searchForArticles}")
    private String name;

    @Value("${app.gui.searchForArticles-button-text:Search}")
    private String searchButtonText;

    @Value("${app.gui.choose-file-button-text:Upload}")
    private String chooseFileButtonText;

    @Value("${app.gui.searchForArticles-field-height:30}")
    private int searchFieldHeight;

    @Value("${app.gui.searchForArticles-field-width:200}")
    private int searchFieldWidth;

    @Value("${app.gui.result-area-height:300}")
    private int resultAreaHeight;

    @Value("${app.gui.result-area-width:300}")
    private int resultAreaWidth;

    //TODO: add more fancy stuff
}
