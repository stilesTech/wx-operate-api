package com.wx.operate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "article")
@Proxy(lazy = false)
public class Article {
    @Id
    @GeneratedValue
    private int id;
    @Column(name="md5")
    private String md5;
    @Column(name="url")
    private String url;
    @Column(name="content")
    private String content;
    @Column(name="title")
    private String title;
}
