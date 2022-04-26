package com.wx.operate.pojo.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stiles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "媒体对象")
public class MediaQuery extends BaseQuery {
    private String mediaId;
}
