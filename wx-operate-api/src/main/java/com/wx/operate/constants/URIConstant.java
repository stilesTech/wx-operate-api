package com.wx.operate.constants;

public class URIConstant {
    public static String MENU_CREATE_URL ="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static String MENU_GET_URL="https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

    public static String MENU_DELETE_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    public static String ACCESS_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={secret}";

    public static String MEDIA_UPLOAD_URL="https https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public static String MEDIA_GET_URL="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    public static String ADD_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

    public static String GET_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

    public static String GET_MATERIALCOUNT_URL="https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";

    public static String BATCHGET_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    public static String UPLOAD_IMG_URL="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

    public static String ADD_NEWS_URL="https://api.weixin.qq.com/cgi-bin/draft/add?access_token=ACCESS_TOKEN";

    public static String UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/draft/add?access_token=ACCESS_TOKEN";
}
