package com.wx.operate.pojo.news;

import lombok.Data;

@Data
public class NewsDetail {

    private String title;

    private String author;

    private String digest;

    private String content;

    private String content_source_url;

    private String thumb_media_id;

    private int show_cover_pic;

    private String url;

    private String thumb_url;

    private int need_open_comment;

    private int only_fans_can_comment;
}
