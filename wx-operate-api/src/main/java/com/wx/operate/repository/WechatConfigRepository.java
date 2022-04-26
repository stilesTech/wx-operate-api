package com.wx.operate.repository;

import com.wx.operate.entity.WechatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author stiles
 */
@Repository
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Integer> {
}
