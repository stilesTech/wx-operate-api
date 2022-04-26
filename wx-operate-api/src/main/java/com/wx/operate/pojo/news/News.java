package com.wx.operate.pojo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新闻消息发送对象")
public class News {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图文消息的封面图片素材id（必须是永久 media_ID）")
    private String thumb_media_id;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空")
    private String digest;

    @ApiModelProperty(value = "是否显示封面，0为false，即不显示，1为true，即显示")
    private Integer show_cover_pic;

    @ApiModelProperty(value = "图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS")
    private String content;

    @ApiModelProperty(value = "图文消息的原文地址，即点击“阅读原文”后的URL")
    private String content_source_url;

    @ApiModelProperty(value = "是否打开评论，0不打开，1打开")
    private Integer need_open_comment;

    @ApiModelProperty(value = "是否粉丝才可评论，0所有人可评论，1粉丝才可评论")
    private Integer only_fans_can_comment;

    private Integer wechatConfigId;
}
