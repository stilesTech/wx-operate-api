package com.wx.operate.service;

import com.wx.operate.entity.Material;
import com.wx.operate.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    @Autowired
    MaterialRepository materialRepository;

    public void save(Material material){
         materialRepository.save(material);
    }

}
