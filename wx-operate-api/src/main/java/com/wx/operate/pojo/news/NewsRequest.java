package com.wx.operate.pojo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stiles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新闻请求实体")
public class NewsRequest {
    @ApiModelProperty(value = "新闻的id集合，用逗号隔开")
    private String ids;

    @ApiModelProperty(value = "图文消息的封面图片素材id（必须是永久 media_ID）")
    private String thumb_media_id;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "是否显示封面，0为false，即不显示，1为true，即显示")
    private Integer show_cover_pic;

    @ApiModelProperty(value = "是否打开评论，0不打开，1打开")
    private Integer need_open_comment;

    @ApiModelProperty(value = "是否粉丝才可评论，0所有人可评论，1粉丝才可评论")
    private Integer only_fans_can_comment;

    private Integer wechatConfigId;

}
