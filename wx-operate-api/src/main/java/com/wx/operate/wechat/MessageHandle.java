package com.wx.operate.wechat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wx.operate.pojo.message.*;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class MessageHandle {

//    @Autowired
//    private UploadUtil uploadUtil;

    //====================================================================================
    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：语音
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：链接
     */
    public final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：小视频
     */
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

    /**
     * 请求消息类型：事件推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 消息返回类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 消息返回类型:语音
     */
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 消息返回类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 消息返回类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 消息返回类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 事件类型:订阅
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：取消订阅
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    //=============================================================================
    /**
     * 事件类型：scan(关注用户扫描带参二维码)
     */
    public final String EVENT_TYPE_SCAN = "scan";

    /**
     * 事件类型：location(上报地理位置)
     */
    public final String EVENT_TYPE_LOCATION = "location";

    /**
     * 事件类型：CLICK(点击菜单拉取消息)
     */
    public final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单URl视图)
     */
    public final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：TEMPLATESENDJOBFINISH(模板消息送达情况提醒)
     */
    public final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";


    /**
     * @Description: 解析微信服务器发过来的xml格式的消息将其转换为map
     * @Parameters: WeixinMessageUtil
     * @Return: Map<String, String>
     * @Create Date: 2017年10月11日上午11:41:23
     * @Version: V1.00
     * @author:来日可期
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {

        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中得到输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到XML的根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 判断又没有子元素列表
        if (elementList.size() == 0) {
            map.put(root.getName(), root.getText());
        } else {
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        System.out.println("---------xml转换为map-----:" + map);
        return map;
    }

    /**
     * 回复关注
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public String subscribeForText(String toUserName, String fromUserName) {

        log.info("有人关注你的公众号了！");
        TextMessage textMessage = new TextMessage();
        //必填
        textMessage.setFromUserName(toUserName);
        //必填
        textMessage.setToUserName(fromUserName);
        //必填
        textMessage.setMsgType(MessageHandle.REQ_MESSAGE_TYPE_TEXT);
        //必填
        textMessage.setContent("帅锅美女，谢谢你的关注：你可以随便发文字图片试试，有惊喜哦！……");
        textMessage.setCreateTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        String xmlString = MessageHandle.textMessageToXml(textMessage);
        log.info(xmlString);

        return xmlString;

    }

    /**
     * 回复取消关注
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public String unsubscribeForText(String toUserName, String fromUserName) {

        log.info(fromUserName + "取消了对你【" + toUserName + "】的微信公众号关注");
        TextMessage textMessage = new TextMessage();
        //必填
        textMessage.setFromUserName(toUserName);
        //必填
        textMessage.setToUserName(fromUserName);
        //必填
        textMessage.setMsgType(MessageHandle.REQ_MESSAGE_TYPE_TEXT);
        //必填
        textMessage.setContent(fromUserName + "取消了对你【" + toUserName + "】的微信公众号关注");
        textMessage.setCreateTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        String xmlString = MessageHandle.textMessageToXml(textMessage);
        log.info(xmlString);
        return xmlString;

    }

    /**
     * 回复文本消息
     *
     * @param toUserName
     * @param fromUserName
     * @param msg
     * @return
     */
    public String replyForText(String toUserName, String fromUserName, String msg) {

        log.info("这是文本消息回复！");
        TextMessage textMessage = new TextMessage();
        //必填
        textMessage.setFromUserName(toUserName);
        //必填
        textMessage.setToUserName(fromUserName);
        //必填
        textMessage.setMsgType(MessageHandle.REQ_MESSAGE_TYPE_TEXT);
        //必填
        textMessage.setContent(msg);
        textMessage.setCreateTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        String xmlString = MessageHandle.textMessageToXml(textMessage);
        log.info(xmlString);
        return xmlString;

    }

    /**
     * 回复文本图片
     *
     * @param toUserName
     * @param fromUserName
     * @param filePath
     * @return
     */
    public String replyForImage(String toUserName, String fromUserName, String filePath) throws Exception {

        log.info("这是图片消息回复！");
        ImageMessage imageMessage = new ImageMessage();
        //必填
        imageMessage.setFromUserName(toUserName);
        //必填
        imageMessage.setToUserName(fromUserName);
        //必填
        imageMessage.setMsgType(MessageHandle.REQ_MESSAGE_TYPE_IMAGE);
        //必填
        imageMessage.setCreateTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

//        String fileMediaId = uploadUtil.uploadFile(filePath, TypeConstant.REQ_MESSAGE_TYPE_IMAGE);
//        if (!StringUtils.isEmpty(fileMediaId)) {
//            Image image = new Image();
//            image.setMediaId(fileMediaId);
//            imageMessage.setImage(image);
//            String xmlString = MessageHandle.imageMessageToXml(imageMessage);
//            log.info(xmlString);
//
//            return xmlString;
//        }
        return null;
    }


    //===================================================================================================

    /**
     * @param textMessage
     * @return xml
     * @Description: 文本消息对象转换成xml
     * @date 2016-12-01
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * @param newsMessage
     * @return xml
     * @Description: 图文消息对象转换成xml
     * @date 2016-12-01
     */

    public String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
//        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * @param imageMessage
     * @return xml
     * @Description: 图片消息对象转换成xml
     * @date 2016-12-01
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }


    /**
     * @param voiceMessage
     * @return xml
     * @Description: 语音消息对象转换成xml
     * @date 2016-12-01
     */
    public String voiceMessageToXml(VoiceMessage voiceMessage) {
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }

    /**
     * @param videoMessage
     * @return xml
     * @Description: 视频消息对象转换成xml
     * @date 2016-12-01
     */
    public String videoMessageToXml(VideoMessage videoMessage) {
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }

    /**
     * @param musicMessage
     * @return xml
     * @Description: 音乐消息对象转换成xml
     * @date 2016-12-01
     */
    public String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 对象到xml的处理
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
