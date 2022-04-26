package com.wx.operate.service;

import com.wx.operate.entity.WechatConfig;
import com.wx.operate.repository.WechatConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatConfigService {
    @Autowired
    WechatConfigRepository wechatConfigRepository;

    public WechatConfig getById(int id){
        return wechatConfigRepository.getOne(id);
    }
}
