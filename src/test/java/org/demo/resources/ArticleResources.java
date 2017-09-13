package org.demo.resources;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

public final class ArticleResources {

    public static final String DIRECTORY_NAME = "articles";

    public static final File ARTICLE_DIRECTORY;

    public static final Map<String, File> ARTICLE_FILES;

    public static final String PROCESSOR_TECHNOLOGY_FILE_NAME = "processor_technology.html";

    static {
        try {
            ARTICLE_DIRECTORY = new ClassPathResource(DIRECTORY_NAME).getFile();
            ARTICLE_FILES = Arrays.stream(ARTICLE_DIRECTORY.listFiles())
                    .collect(Collectors.toMap(f -> f.getName().toLowerCase(), Function.identity()));
        } catch (Exception ex) {
            throw new RuntimeException(format("Unable to find '%s'", DIRECTORY_NAME), ex);
        }
    }

    private ArticleResources() {
    }
}
