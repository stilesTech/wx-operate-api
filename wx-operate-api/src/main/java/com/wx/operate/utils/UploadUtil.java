package com.wx.operate.utils;

import com.alibaba.fastjson.JSONObject;
import com.wx.operate.constants.URIConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class UploadUtil {

    @Autowired
    AccessTokenUtil accessTokenUtil;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 上传临时素材
     * 1、临时素材media_id是可复用的。
     * 2、媒体文件在微信后台保存时间为3天，即3天后media_id失效。
     * 3、上传临时素材的格式、大小限制与公众平台官网一致。
     * @param filePath
     * @param type
     * @return
     */
    public String uploadFile(String accessToken,String filePath,String type) {

        if (accessToken != null) {
            String url = URIConstant.MEDIA_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken)
                    .replace("TYPE", type);
            log.info("MEDIA_UPLOAD_URL:{}",url);

            //设置请求体，注意是LinkedMultiValueMap
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            form.add("media", fileSystemResource);

            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            //用HttpEntity封装整个请求报文
            HttpEntity<MultiValueMap<String, Object>> data = new HttpEntity<>(form, headers);
            try{
                //这里RestTemplate请求返回的字符串直接转换成JSONObject会报异常,后续深入找一下原因
                String resultString = restTemplate.postForObject(url, data, String.class);
                log.info("上传返回的信息是：{}",resultString);
                if(!StringUtils.isEmpty(resultString)){
                    JSONObject jsonObject = JSONObject.parseObject(resultString);
                    return jsonObject.getString("media_id");
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }

        }
        return null;
    }

    /**
     * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）
     * 1、如果是图片，则下载图片
     */
    public ResponseEntity<byte[]> getImage(String accessToken,String mediaId){

        if(accessToken != null) {
            String url = URIConstant.MEDIA_GET_URL.replace("ACCESS_TOKEN", accessToken)
                    .replace("MEDIA_ID", mediaId);
            log.info("MEDIA_GET_URL:{}", url);

            String fileName = mediaId+ ".jpg";
            HttpHeaders headers = new HttpHeaders();
            try {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentDispositionFormData("attachment", fileName);// 文件名称
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
            return responseEntity;
        }
        return null;
    }

    /**
     * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）
     * 1、如果是声音，则下载声音
     */
    public ResponseEntity<byte[]> getVoice(String accessToken,String mediaId){
        if(accessToken != null) {
            String url = URIConstant.MEDIA_GET_URL.replace("ACCESS_TOKEN", accessToken)
                    .replace("MEDIA_ID", mediaId);
            log.info("MEDIA_GET_URL:{}", url);

            String fileName = mediaId+ ".speex";
            HttpHeaders headers = new HttpHeaders();
            try {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentDispositionFormData("attachment", fileName);// 文件名称
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
            return responseEntity;
        }
        return null;
    }

    /**
     * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）
     * 2、如果是视频，则返回视频的地址
     */
    public String getVedio(String accessToken,String mediaId){
        if(accessToken != null) {
            String url = URIConstant.MEDIA_GET_URL.replace("ACCESS_TOKEN", accessToken)
                    .replace("MEDIA_ID", mediaId);
            log.info("MEDIA_GET_URL:{}", url);

            String responseString = restTemplate.getForObject(url,String.class);
            return responseString;
        }
        return null;
    }
}
