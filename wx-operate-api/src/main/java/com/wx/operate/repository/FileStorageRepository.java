package com.wx.operate.repository;

import com.wx.operate.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author stiles
 */
@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {
    FileStorage findBySourceUrlAndWechatConfigId(String sourceUrl,int wechatConfigId);
}
