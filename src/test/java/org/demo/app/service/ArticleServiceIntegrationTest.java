package org.demo.app.service;

import org.demo.solr.AbstractSolrIntegrationTest;
import org.demo.solr.repository.ArticleRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = {ArticleService.class, ArticleRepository.class})
public class ArticleServiceIntegrationTest extends AbstractSolrIntegrationTest {

    private static final ClassPathResource ARTICLES = new ClassPathResource("articles");

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeClass
    public static void setUpClass() throws Exception {
        File articleDirectory = ARTICLES.getFile();
        assertThat(articleDirectory.isDirectory());
    }

    @Test
    public void shouldAddDocumentToSolrServerForFiles() throws Exception {
        File[] files = ARTICLES.getFile().listFiles();
        articleService.addArticleFiles(a -> {}, ex -> {}, files);

        assertThat(articleRepository.findAll())
            .hasSize(files.length);
    }

    @Test
    public void shouldAddDocumentToSolrServerForDirectories() throws Exception {
        File articleDirectory = ARTICLES.getFile();
        articleService.addArticleFiles(a -> {}, ex -> {}, articleDirectory);

        assertThat(articleRepository.findAll())
                .hasSize(articleDirectory.listFiles().length);
    }
}