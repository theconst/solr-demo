package org.demo.app.service;

import org.demo.parser.ArticleParser;
import org.demo.pojo.Article;
import org.demo.solr.AbstractSolrIntegrationTest;
import org.demo.solr.repository.ArticleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.demo.resources.ArticleResources.ARTICLE_DIRECTORY;
import static org.demo.resources.ArticleResources.ARTICLE_FILES;

@ContextConfiguration(classes = {ArticleService.class, ArticleParser.class, ArticleRepository.class})
public class ArticleServiceIntegrationTest extends AbstractSolrIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleParser articleParser;

    @SpyBean
    private FileReader fileReader;

    @Test
    public void shouldAddDocumentToSolrServerForFiles() throws Exception {
        final AtomicInteger callbacksMade = new AtomicInteger();
        File[] files = ARTICLE_DIRECTORY.listFiles();
        articleService.addArticleFiles(a -> callbacksMade.incrementAndGet(), ex -> fail(ex.getMessage()), files);

        assertThat(callbacksMade).hasValue(ARTICLE_FILES.size());
        assertThat(articleRepository.findAll())
                .hasSize(files.length);
    }

    @Test
    public void shouldAddDocumentToSolrServerForDirectories() throws Exception {
        final AtomicInteger callbacksMade = new AtomicInteger(0);
        articleService.addArticleFiles(a -> callbacksMade.incrementAndGet(), ex -> fail(ex.getMessage()), ARTICLE_DIRECTORY);

        int numberOfFiles = ARTICLE_FILES.size();
        assertThat(callbacksMade)
                .hasValue(numberOfFiles);
        assertThat(articleRepository.findAll())
                .isNotEmpty()
                .hasSize(numberOfFiles);
    }

    @Test
    public void shouldAddDocumentsWithCreatedAtAndTitle() throws Exception {
        final AtomicInteger callbacksMade = new AtomicInteger(0);
        articleService.addArticleFiles(a -> callbacksMade.incrementAndGet(), ex -> fail(ex.getMessage()), ARTICLE_DIRECTORY);

        Iterable<Article> articles = articleRepository.findAll();

        assertThat(callbacksMade).hasValue(ARTICLE_FILES.size());
        assertThat(StreamSupport.stream(articles.spliterator(), false)
                .filter(a -> Objects.nonNull(a.getCreatedAt()))
                .collect(Collectors.toList()))
                .isNotEmpty()
                .containsExactlyElementsOf(articles);
    }

    @Test
    public void shouldSearchByContent() throws Exception {
        Article article = Article.builder()
                    .fileName("fname")
                    .title("title")
                    .content("content")
                    .build();

        articleRepository.save(article);

        assertThat(articleRepository.findByContent("content").get())
                .isEqualTo(singletonList(article));
    }

    @Test
    public void shouldCategorizeItems() throws Exception {
        final AtomicInteger callbacksMade = new AtomicInteger(0);
        articleService.addArticleFiles(a -> callbacksMade.incrementAndGet(), ex -> fail(ex.getMessage()), ARTICLE_DIRECTORY);

        Article article = Article.builder()
                .title("Processor Technology")
                .content("Processor Technology")
                .fileName("fname")
                .build();

        articleRepository.save(article);


        Optional<Article> classiffiedArticle = articleRepository.findById("fname");

        assertThat(classiffiedArticle)
                .isPresent();

        assertThat(classiffiedArticle.get().getCategories()).isNotEmpty();
    }
}