package com.wx.operate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @author stiles
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wechat_config")
@Proxy(lazy = false)
public class WechatConfig {
    @Id
    @GeneratedValue
    private int id;
    @Column(name="code")
    private String code;
    @Column(name="name")
    private String name;
    @Column(name="app_id")
    private String appId;
    @Column(name="app_secret")
    private String appSecret;
    @Column(name="description")
    private String description;
    @Column(name="article_menu_cover")
    private String articleMenuCover;
    @Column(name="article_cover")
    private String articleCover;
}
