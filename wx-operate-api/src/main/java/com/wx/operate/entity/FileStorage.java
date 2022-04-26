package com.wx.operate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * @author stiles
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "file_storage")
@Proxy(lazy = false)
public class FileStorage {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    @Column(name="source_url")
    private String sourceUrl;
    @Column(name="file_streams")
    private byte[] fileStreams;

    private String type;
    @Column(name="create_time")
    private Date createTime;

    @Column(name="wechat_config_id")
    private int wechatConfigId;

}
