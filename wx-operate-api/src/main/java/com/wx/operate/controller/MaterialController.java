package com.wx.operate.controller;

import com.google.common.cache.Cache;
import com.wx.operate.common.cache.CacheClient;
import com.wx.operate.common.utils.JsonUtil;
import com.wx.operate.pojo.material.MaterialResponse;
import com.wx.operate.pojo.query.MaterialQuery;
import com.wx.operate.utils.MaterialUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author stiles
 */
@RestController
@RequestMapping("material")
@Api(tags= "微信素材接口")
@Slf4j
public class MaterialController {

    @Autowired
    MaterialUtil materialUtil;

    /**
     * 添加永久素材列表
     */
    @ApiOperation(value = "添加永久素材FilePath")
    @RequestMapping(value = "/addMaterialFilePath", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="source",value="来源", paramType="query",dataType="String"),
            @ApiImplicitParam(name="filePath",value="文件路径", paramType="query",dataType="String"),
            @ApiImplicitParam(name="title",value="视频素材的标题", paramType="query",dataType="String"),
            @ApiImplicitParam(name="introduction",value="视频素材的描述", paramType="query",dataType="String"),
            @ApiImplicitParam(name="type",value="素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）", paramType="query",dataType="String")
    })
    public MaterialResponse addMaterialFilePath(MaterialQuery materialQuery) {
        Cache<String,String> cache =  CacheClient.getLocalCache();
        String cacheKey = getCacheKey(materialQuery);
        String materialValue = cache.getIfPresent(cacheKey);
        if(StringUtils.isNotBlank(materialValue)){
           return JsonUtil.deserialize(materialValue,MaterialResponse.class);
        }

        MaterialResponse material = materialUtil.addMaterialFilePath(materialQuery);

        if(StringUtils.isNotBlank(material.getMedia_id())){
            cache.put(cacheKey,JsonUtil.serialize(material));
        }

        return material;
    }

    private String getCacheKey(MaterialQuery materialQuery){
        return "cache_file_storage"+materialQuery.getFileStorageId();
    }
}
