package com.wx.operate.utils;

import com.alibaba.fastjson.JSONObject;
import com.wx.operate.common.utils.FileUtil;
import com.wx.operate.common.utils.JsonUtil;
import com.wx.operate.constants.URIConstant;
import com.wx.operate.entity.FileStorage;
import com.wx.operate.pojo.material.MaterialResponse;
import com.wx.operate.pojo.news.Articles;
import com.wx.operate.pojo.query.MaterialQuery;
import com.wx.operate.service.FileStorageService;
import com.wx.operate.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 功能：图文素材工具类
 */
@Slf4j
@Component
public class NewsUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    MaterialService materialService;

    /**
     *上传图文消息素材
     */
    public String uploadNews(String accessToken,Articles articles) throws UnsupportedEncodingException {
        if (accessToken != null) {
            log.info("URL{}", URIConstant.UPLOAD_NEWS_URL);
            String url = URIConstant.UPLOAD_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("UPLOAD_NEWS_URL:{}", url);

            String jsonNews = JSONObject.toJSONString(articles);

            String jsonObject = restTemplate.postForObject(url, jsonNews,String.class);

            return jsonObject;
        }
        return null;
    }

    /**
     * 根据mediaId获取图文素材信息
     */
    public String getNews(String accessToken,String mediaId){
        if(accessToken != null) {
            String url = URIConstant.GET_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("GET_MATERIAL_URL:{}", url);

            JSONObject data = new JSONObject();
            data.put("media_id", mediaId);

            String jsonNews = JSONObject.toJSONString(data);

            String jsonObject = restTemplate.postForObject(url, jsonNews,String.class);

            return jsonObject;
        }
        return null;
    }



    /**
     上传图文消息内的图片获取URL
     */
    public MaterialResponse uploadImg(MaterialQuery materialQuery) {
        String accessToken = accessTokenUtil.getAccessToken(materialQuery.getWechatConfigId());

        if (accessToken != null) {
            String url = URIConstant.UPLOAD_IMG_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("UPLOAD_IMG_URL:{}",url);
            log.info("accessToken:{}",accessToken);
            //设置请求体，注意是LinkedMultiValueMap
            MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();

            String filePath = materialQuery.getFilePath();
            FileStorage fileStorage = fileStorageService.getById(materialQuery.getFileStorageId());
            if(fileStorage!=null){
                Path path=  Paths.get(FileUtil.getResourcePath(),"upload",fileStorage.getName());
                FileUtil.byteToFile(fileStorage.getFileStreams(),path.toString());
                filePath = path.toString();
            }

            //设置上传文件
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            data.add("media", fileSystemResource);

            //上传文件,设置请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            httpHeaders.setContentLength(fileSystemResource.getFile().length());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data,
                    httpHeaders);
            try{
                String resultJSON = restTemplate.postForObject(url, requestEntity, String.class);
                log.info("上传返回的信息是：{}",resultJSON);
                MaterialResponse response = JsonUtil.deserialize(resultJSON, MaterialResponse.class);
                return response;
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return null;

    }

}
