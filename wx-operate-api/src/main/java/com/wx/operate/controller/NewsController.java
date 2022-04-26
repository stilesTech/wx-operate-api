package com.wx.operate.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.wx.operate.common.cache.CacheClient;
import com.wx.operate.common.utils.JsonUtil;
import com.wx.operate.entity.Article;
import com.wx.operate.pojo.material.MaterialResponse;
import com.wx.operate.pojo.news.Articles;
import com.wx.operate.pojo.news.News;
import com.wx.operate.pojo.news.NewsRequest;
import com.wx.operate.pojo.news.NewsResponse;
import com.wx.operate.pojo.query.MaterialQuery;
import com.wx.operate.pojo.query.MediaQuery;
import com.wx.operate.service.ArticleService;
import com.wx.operate.utils.AccessTokenUtil;
import com.wx.operate.utils.NewsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author stiles
 */
@RestController
@RequestMapping("news")
@Api(tags = "图文素材接口")
@Slf4j
public class NewsController {

    @Autowired
    NewsUtil newsUtil;
    @Autowired
    ArticleService articleService;
    @Autowired
    AccessTokenUtil accessTokenUtil;

    @ApiOperation(value = "上传图文消息素材")
    @RequestMapping(value = "/uploadNews", method = RequestMethod.POST)
    public Object uploadNews(News news) throws Exception {
        Articles articles=new Articles();
        articles.setArticles(Arrays.asList(news));
        String accessToken = accessTokenUtil.getAccessToken(news.getWechatConfigId());
        String result = newsUtil.uploadNews(accessToken,articles);
        log.info("result:{}", JSONObject.parseObject(result).toJSONString());
        return result;
    }

    @ApiOperation(value = "上传图文消息素材列表")
    @RequestMapping(value = "/uploadNewsList", method = RequestMethod.POST)
    public Object uploadNewsList(NewsRequest request) throws Exception {
        List<Article> list=  articleService.getArticles(request.getIds());
        List<News> newsList = convertNews(list,request);
        Articles articles=new Articles();
        articles.setArticles(newsList);
        String accessToken = accessTokenUtil.getAccessToken(request.getWechatConfigId());
        String result = newsUtil.uploadNews(accessToken,articles);
        log.info("result:{}", JSONObject.parseObject(result).toJSONString());
        return result;
    }

    private List<News> convertNews(List<Article> articles,NewsRequest request){
        List<News> newsList =new ArrayList<>();
        for(Article article : articles){
            News news =new News();
            news.setAuthor(request.getAuthor());
            news.setContent(article.getContent());
            news.setDigest("");
            news.setContent_source_url("");
            news.setNeed_open_comment(request.getNeed_open_comment());
            news.setTitle(article.getTitle());
            news.setOnly_fans_can_comment(request.getOnly_fans_can_comment());
            news.setShow_cover_pic(request.getShow_cover_pic());
            news.setThumb_media_id(request.getThumb_media_id());
            newsList.add(news);
        }
        return newsList;
    }

    @ApiOperation(value = "上传图文消息内的图片获取URL")
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public MaterialResponse uploadImg(MaterialQuery materialQuery) {
        Cache<String,String> cache =  CacheClient.getLocalCache();
        String cacheKey = getCacheKey(materialQuery);
        String materialValue = cache.getIfPresent(cacheKey);
        if(StringUtils.isNotBlank(materialValue)){
            return JsonUtil.deserialize(materialValue,MaterialResponse.class);
        }
        MaterialResponse material = newsUtil.uploadImg(materialQuery);
        if(StringUtils.isNotBlank(material.getUrl())){
            cache.put(cacheKey,JsonUtil.serialize(material));
        }
        return material;
    }

    private String getCacheKey(MaterialQuery materialQuery){
        return "cache_file_storage"+materialQuery.getFileStorageId();
    }

    @ApiOperation(value = "获取图文信息")
    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    public NewsResponse getNews(MediaQuery mediaQuery){
        String accessToken = accessTokenUtil.getAccessToken(mediaQuery.getWechatConfigId());
        String result =  newsUtil.getNews(accessToken,mediaQuery.getMediaId());
        log.info("result:{}", JSONObject.parseObject(result).toJSONString());
        NewsResponse response = JsonUtil.deserialize(result, NewsResponse.class);
        return response;
    }
}
