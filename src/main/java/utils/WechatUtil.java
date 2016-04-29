package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * Created by sdww on 2016/4/30.
 */
public class WechatUtil {

    public static String accessToken;

    public static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx670a420a7e8fbaa1&secret=c83316595a42319d21ce59101ebea32c";

    private static final String baseUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    private static final String userOpenId = "oZIE1wjDVibVnXstHH_F4TnGMmyM";

    public void getAccessToken() {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(accessTokenUrl);
        try {
            int i = httpClient.executeMethod(getMethod);
            if(i == 200) {
                JSONObject jsonObject = JSONObject.parseObject(getMethod.getResponseBodyAsString());
                accessToken = (String)jsonObject.get("access_token");
            }
            System.out.println(accessToken);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }

    public void send(String message) {
        getAccessToken();
        String url = baseUrl + accessToken;
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        String baseBody = "{\"touser\":\"OPENID\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hello World\"}}";
        JSONObject jsonObject = JSONObject.parseObject(baseBody);
        jsonObject.replace("touser", userOpenId);
        JSONObject text = jsonObject.getJSONObject("text");
        text.replace("content", message);

        String str = jsonObject.toJSONString();

        try {
            RequestEntity requestEntity = new StringRequestEntity(str, "text/xml","UTF-8");
            postMethod.setRequestEntity(requestEntity);
            int status = httpClient.executeMethod(postMethod);
            if(status == 200) {
                System.out.print(postMethod.getResponseBodyAsString());
            }
        }catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }


}
