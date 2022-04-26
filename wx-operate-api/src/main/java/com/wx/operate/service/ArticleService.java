package com.wx.operate.service;

import com.google.common.cache.Cache;
import com.wx.operate.common.cache.CacheClient;
import com.wx.operate.entity.Article;
import com.wx.operate.repository.ArticleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getArticles(String ids){
        String[] listIds = StringUtils.split(ids,',');
        List<Integer> list = new ArrayList<>();
        for(String item : listIds){
            list.add(Integer.valueOf(item));
        }
        List<Article> articles= articleRepository.findAllById(list);
        return articles;
    }

    public String getUrl(String md5){
        Cache<String,String> cache =  CacheClient.getLocalCache();
        String url = cache.getIfPresent(md5);
        if("0".equals(url)){
            return null;
        }
        if(StringUtils.isBlank(url)){
            url = articleRepository.getUrl(md5);
            if(StringUtils.isNotBlank(url)){
                cache.put(md5,url);
            }else{
                CacheClient.getLocalShortCache().put(md5,"0");
            }
        }
        return url;
    }

}
