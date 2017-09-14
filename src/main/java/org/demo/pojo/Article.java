package org.demo.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(exclude = "score")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SolrDocument(solrCoreName = "wikipedia")
public class Article {

    @Id
    @Field("id")
    private String fileName;

    @Indexed(name = "title_txt", required = true)
    private String title;

    @Indexed(name = "content_txt", required = true)
    private String content;

    @Indexed(name = "attr_categories")
    private List<String> categories;

    @Indexed(name = "created_at_dt")
    private LocalDateTime createdAt;

    @Score
    private Float score;
}
