package org.demo.pojo;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

@Data
public class Article {

    @Field
    private String fileName;

    @Field("title")
    private List<String> titles;

    //TODO: add handling of content
    @Field("content")
    private String content;
}
