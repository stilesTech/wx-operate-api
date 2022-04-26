package com.wx.operate.utils;

import com.alibaba.fastjson.JSONObject;
import com.wx.operate.common.utils.FileUtil;
import com.wx.operate.common.utils.JsonUtil;
import com.wx.operate.common.utils.TimeUtil;
import com.wx.operate.constants.URIConstant;
import com.wx.operate.entity.FileStorage;
import com.wx.operate.entity.Material;
import com.wx.operate.pojo.material.MaterialResponse;
import com.wx.operate.pojo.query.MaterialQuery;
import com.wx.operate.service.FileStorageService;
import com.wx.operate.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Slf4j
@Component
public class MaterialUtil {

    @Autowired
    AccessTokenUtil accessTokenUtil;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MaterialService materialService;
    @Autowired
    FileStorageService fileStorageService;

    /**
     * 添加永久素材
     * 新增永久视频素材需特别注意,在上传视频素材时需要POST另一个表单，包含素材的描述信息，内容格式为JSON
     */
    public MaterialResponse addMaterialFilePath(MaterialQuery materialQuery) {
        String accessToken = accessTokenUtil.getAccessToken(materialQuery.getWechatConfigId());
        if (accessToken != null) {
            String url = URIConstant.ADD_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken)
                    .replace("TYPE", materialQuery.getType());
            log.info("ADD_MATERIAL_URL:{}",url);
            log.info("materialQuery.getFilePath:{}",materialQuery.getFilePath());
            //设置请求体，注意是LinkedMultiValueMap
            MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
            if("video".equalsIgnoreCase(materialQuery.getType())){
                if(!StringUtils.isEmpty(materialQuery.getTitle()) && !StringUtils.isEmpty(materialQuery.getIntroduction())){
                    data.add("title", materialQuery.getTitle());
                    data.add("introduction", materialQuery.getIntroduction());
                }
            }

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
            httpHeaders.setContentType(MULTIPART_FORM_DATA);
            httpHeaders.setContentLength(fileSystemResource.getFile().length());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data,
                    httpHeaders);
            try{
                //这里RestTemplate请求返回的字符串直接转换成JSONObject会报异常,后续深入找一下原因
                String resultString = restTemplate.postForObject(url, requestEntity, String.class);
                log.info("上传返回的信息是：{}",resultString);
                MaterialResponse response = JsonUtil.deserialize(resultString, MaterialResponse.class);
                if(StringUtils.isNotBlank(response.getMedia_id())){
                    Material material = createMaterial(materialQuery,response);
                    materialService.save(material);
                }
                return response;
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return null;
    }

    public static Material createMaterial(MaterialQuery query,MaterialResponse response){
        Material material=new Material();
        material.setCreateTime(TimeUtil.getCurrentDate())
                .setFilePath(query.getFilePath())
                .setIntroduction(query.getIntroduction())
                .setType(query.getType())
                .setTitle(query.getTitle())
                .setMediaId(response.getMedia_id())
                .setWechatConfigId(query.getWechatConfigId())
                .setUrl(response.getUrl());
        return material;
    }


    /**
     * 根据mediaId获取永久素材
     */
    public ResponseEntity<byte[]> downloadMaterialImage(String mediaId,int wechatConfigId){

        String accessToken = accessTokenUtil.getAccessToken(wechatConfigId);
        if(accessToken != null) {
            String url = URIConstant.GET_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("GET_MATERIAL_URL:{}", url);

            String fileName = mediaId+ ".jpg";
            JSONObject data = new JSONObject();
            data.put("media_id", mediaId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MULTIPART_FORM_DATA);

            try {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentDispositionFormData("attachment", fileName);// 文件名称
            ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, data, byte[].class);
            return responseEntity;
        }
        return null;
    }

    /**
     * 获取素材数量
     * @return
     */
    public String getMaterialCount(int wechatConfigId){

        String accessToken = accessTokenUtil.getAccessToken(wechatConfigId);
        if(accessToken != null){
            String url = URIConstant.GET_MATERIALCOUNT_URL.replace("ACCESS_TOKEN", accessToken);
            //发起GET请求
            String resultString = restTemplate.getForObject(url, String.class);
            return resultString;
        }
        return null;
    }

    /**
     * 获取素材列表
     * @return
     */
    public String batchGetMaterial(int wechatConfigId,String type, Integer offset, Integer count){

        String accessToken = accessTokenUtil.getAccessToken(wechatConfigId);
        if(accessToken != null){
            String url = URIConstant.BATCHGET_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("BATCHGET_MATERIAL_URL:{}",url);

            JSONObject jsonObject = new JSONObject();
            //素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
            jsonObject.put("type", type);
            //从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
            jsonObject.put("offset", offset);
            //返回素材的数量，取值在1到20之间
            jsonObject.put("count", count);

            //发起POST请求
            String resultString = restTemplate.postForObject(url, jsonObject.toJSONString(),String.class);
            return resultString;
        }
        return null;
    }
}
