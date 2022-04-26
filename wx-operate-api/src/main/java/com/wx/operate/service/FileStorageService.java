package com.wx.operate.service;

import com.wx.operate.entity.FileStorage;
import com.wx.operate.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

//    public FileStorage getBySourceUrlAndWechatConfigId(String sourceUrl, int wechatConfigId){
//        return fileStorageRepository.findBySourceUrlAndWechatConfigId(sourceUrl,wechatConfigId);
//    }

    public FileStorage getById(Integer id){
        return fileStorageRepository.getOne(id);
    }
}
