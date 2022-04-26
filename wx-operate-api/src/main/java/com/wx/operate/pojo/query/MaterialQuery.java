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
@ApiModel(value = "素材发送对象")
public class MaterialQuery extends BaseQuery{
    private String filePath;
    private String type;
    private String title;
    private String introduction;
//    private String source;
    private Integer fileStorageId;
}
