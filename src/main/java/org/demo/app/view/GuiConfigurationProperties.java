package org.demo.app.view;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class GuiConfigurationProperties {

    @Value("${app.gui.name?:wiki-search}")
    private String name;

    @Value("${app.gui.search-button-text?:Search}")
    private String searchButtonText;

    @Value("${app.gui.choose-file-button-text?:Upload}")
    private String chooseFileButtonText;

    //TODO: add more fancy stuff
}
