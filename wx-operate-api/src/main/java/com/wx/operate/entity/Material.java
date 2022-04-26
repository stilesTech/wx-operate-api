package com.wx.operate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "material")
@Proxy(lazy = false)
public class Material {
    @Id
    @GeneratedValue
    private int id;
    @Column(name="file_path")
    private String filePath;
    private String title;
    private String introduction;
    private String type;
    @Column(name="media_id")
    private String mediaId;
    private String url;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="source")
    private String source;
    @Column(name="wechat_config_id")
    private int wechatConfigId;
}
