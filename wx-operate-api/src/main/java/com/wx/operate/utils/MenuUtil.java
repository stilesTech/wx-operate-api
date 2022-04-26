package com.wx.operate.utils;

import com.alibaba.fastjson.JSONObject;
import com.wx.operate.constants.URIConstant;
import com.wx.operate.pojo.menu.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class MenuUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    public Menu createMenuString() {
        ViewButton btn11 = new ViewButton();
        btn11.setName("百度一下");
        btn11.setType("view");
        btn11.setUrl("https://www.baidu.com/");

        ViewButton btn12 = new ViewButton();
        btn12.setName("小米官网");
        btn12.setType("view");
        btn12.setUrl("https://www.mi.com/");

        ViewButton btn13 = new ViewButton();
        btn13.setName("聊天室");
        btn13.setType("view");
        btn13.setUrl("http://r7fkc2.natappfree.cc/template/chat_room");

        ViewButton btn14 = new ViewButton();
        btn14.setName("今日头条");
        btn14.setType("view");
        btn14.setUrl("https://www.toutiao.com/");

        ClickButton btn21 = new ClickButton();
        btn21.setName("歌曲点播");
        btn21.setType("click");
        btn21.setKey("21");

        ClickButton btn22 = new ClickButton();
        btn22.setName("经典游戏");
        btn22.setType("click");
        btn22.setKey("22");

        ClickButton btn23 = new ClickButton();
        btn23.setName("美女电台");
        btn23.setType("click");
        btn23.setKey("23");

        ClickButton btn24 = new ClickButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        ClickButton btn25 = new ClickButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        ClickButton btn31 = new ClickButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        ClickButton btn32 = new ClickButton();
        btn32.setName("电影排行榜");
        btn32.setType("click");
        btn32.setKey("32");

        ClickButton btn33 = new ClickButton();
        btn33.setName("幽默笑话");
        btn33.setType("click");
        btn33.setKey("33");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("门户官网");
        mainBtn1.setSub_button(new Button[]{btn11, btn12, btn13, btn14});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_button(new Button[]{btn21, btn22, btn23, btn24, btn25});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[]{btn31, btn32, btn33});

        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1, mainBtn2, mainBtn3});

        return menu;

    }

    public JSONObject creatMenu(String accessToken) {

        Menu menu = this.createMenuString();
        if (accessToken != null) {
            log.info("URL{}", URIConstant.MENU_CREATE_URL);
            String url = URIConstant.MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
            log.info("URL_ACCESS_TOKEN:{}", url);
            //将菜单对象转换成JSON字符串
            String jsonMenu = JSONObject.toJSONString(menu);
            log.info("JSONMENU:{}", jsonMenu);
            //发起POST请求创建菜单
            JSONObject jsonObject = restTemplate.postForObject(url, jsonMenu, JSONObject.class);

            return jsonObject;
        }
        return null;

    }

    /**
     * @Description: 查询菜单
     * @Parameters:
     * @Return:
     * @Create Date: 2018年3月13日下午2:24:02
     * @Version: V1.00
     * @author: 来日可期
     */
    public JSONObject getWXMenu(String accessToken) {
        String requestUrl = URIConstant.MENU_GET_URL.replace("ACCESS_TOKEN", accessToken);
        //发起GET请求查询菜单
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
        return jsonObject;

    }

    /**
     * @Description: 删除菜单
     * @Parameters:
     * @Return:
     * @Create Date: 2018年3月13日下午2:31:15
     * @Version: V1.00
     * @author: 来日可期
     */
    public JSONObject deleteMenu(String accessToken) {

        String requestUrl = URIConstant.MENU_DELETE_URL.replace("ACCESS_TOKEN", accessToken);
        //发起GET请求删除菜单
        JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);

//        if (null != jsonObject) {
//            int errorCode = jsonObject.getIntValue("errcode");
//            String errorMsg = jsonObject.getString("errmsg");
//            if (0== errorCode) {
//                result = true;
//            } else {
//                result = false;
//                log.error("创建菜单失败 errcode：{} errmsg：{} ",errorCode,errorMsg);
//            }
//        }
        return jsonObject;

    }
}
