package org.demo.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SolrDocument(solrCoreName = "wikipedia")
public class Article {

    @Id
    @Indexed(name = "fileName")
    private String fileName;

    @Indexed(name = "title")
    private String title;

    @Indexed(name = "content")
    private String content;
}
