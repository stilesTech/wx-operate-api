package com.wx.operate.pojo.news;

import com.wx.operate.pojo.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsResponse extends BaseResponse {
    private List<NewsDetail> news_item;
}

