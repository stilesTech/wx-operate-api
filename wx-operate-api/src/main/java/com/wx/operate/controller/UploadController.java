package com.wx.operate.controller;

import com.wx.operate.utils.AccessTokenUtil;
import com.wx.operate.utils.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author stiles
 */
@RestController
@RequestMapping("upload")
@Slf4j
@Api(tags = "微信素材上传接口")
public class UploadController {

    @Autowired
    UploadUtil uploadUtil;
    @Autowired
    AccessTokenUtil accessTokenUtil;

    @ApiOperation(value = "上传临时素材")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="filePath",value="文件位置", paramType="query",dataType="String"),
            @ApiImplicitParam(name="type",value="媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）", paramType="query",dataType="String"),

    })

    public Object upload(int wechatConfigId,String filePath, String type) throws Exception{
        String accessToken =accessTokenUtil.getAccessToken(wechatConfigId);
        String result = uploadUtil.uploadFile(accessToken,filePath,type);
        return result;
    }

    @ApiOperation(value = "下载临时素材")
    @RequestMapping(value="/getFile",method = RequestMethod.GET)
    public Object upload(int wechatConfigId,String mediaId){
        String accessToken = accessTokenUtil.getAccessToken(wechatConfigId);
        ResponseEntity<byte[]> result = uploadUtil.getImage(accessToken,mediaId);
        return result;
    }
}
