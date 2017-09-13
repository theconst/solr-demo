package org.demo.parser;

import org.demo.pojo.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.demo.resources.ArticleResources.ARTICLE_FILES;
import static org.demo.resources.ArticleResources.PROCESSOR_TECHNOLOGY_FILE_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArticleParser.class)
public class ArticleParserIntegrationTest {

    private static final Charset CHARSET = Charset.defaultCharset();

    @Autowired
    private ArticleParser articleParser;

    @Test
    public void shouldParseArticle() throws Exception {
        Article article = articleParser.parse(readFileToString(ARTICLE_FILES.get(PROCESSOR_TECHNOLOGY_FILE_NAME), CHARSET));

        assertThat(article.getTitle()).isEqualTo("Processor Technology");
        assertThat(article.getCategories())
                .containsExactly("Information technology company stubs",
                        "Companies established in 1975",
                        "Defunct computer companies of the United States",
                        "Defunct computer hardware companies");
    }

}