package com.wx.operate.controller;

import com.wx.operate.common.wechat.WechatConfig;
import com.wx.operate.wechat.MessageHandle;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author stiles
 */
@RestController
@Api(tags = "接入验证接口")
@RequestMapping("wechat")
@Slf4j
public class WechatController {

    @Autowired
    private MessageHandle messageHandle;

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(@RequestParam(value = "signature") String signature,
                          @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce,
                          @RequestParam(value = "echostr") String echostr) {

        log.info("-----开始校验签名-----");
        PrintWriter out = null;
        if (WechatConfig.checkSignature(signature, timestamp, nonce)) {
            log.info("-----签名校验通过-----");
            return echostr;
        } else {
            log.info("-----校验签名失败-----");
            return null;
        }
    }
    @RequestMapping(value = "connect", method = RequestMethod.POST)
    public String doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");

        //将微信请求xml转为map格式，获取所需的参数
        Map<String, String> map = MessageHandle.parseXml(request);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");
        String Event = map.get("Event");

        if(MessageHandle.REQ_MESSAGE_TYPE_EVENT.equals(MsgType)){
            if(MessageHandle.EVENT_TYPE_SUBSCRIBE.equals(Event)){
                String xmlString = messageHandle.subscribeForText(ToUserName,FromUserName);
                return xmlString;
            }else if(MessageHandle.EVENT_TYPE_UNSUBSCRIBE.equals(Event)){
                String xmlString = messageHandle.unsubscribeForText(ToUserName,FromUserName);
                return xmlString;
            }
        }

        //处理文本类型，实现输入1，回复相应的封装的内容
        if (MessageHandle.REQ_MESSAGE_TYPE_TEXT.equals(MsgType)) {
            String xmlString = messageHandle.replyForText(ToUserName,FromUserName,"你发送的是：" + Content);
            log.info(xmlString);
            return xmlString;
        }

        if (MessageHandle.REQ_MESSAGE_TYPE_IMAGE.equals(MsgType)) {
            String filePath = "C:\\Users\\RonnieXu\\Pictures\\2.jpg";
            String xmlString = messageHandle.replyForImage(ToUserName,FromUserName,filePath);
            return xmlString;
        }

        return null;
    }

}
