package hwz.com.wechatlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class MainActivity extends Activity
{
    Button btnLogin;
    // 自己微信应用的 appId
    public static String WX_APP_ID = "wx500bc3b122ebb5ad";
    // 自己微信应用的 appSecret
    public static String WX_SECRET = "9d358e7815a8e61623ee9f0d087da110";

    public static String WX_CODE = "";

    public static IWXAPI wxApi;
    public static boolean isWXLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.btn_login);
        wxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        wxApi.registerApp(WX_APP_ID);
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                wxApi.registerApp(WX_APP_ID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                wxApi.sendReq(req);
                isWXLogin = true;

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isWXLogin) {
            loadWXUserInfo();
        }
    }
    private void loadWXUserInfo() {
        System.out.println("getUserInfo");
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WX_APP_ID
                + "&secret=" + WX_SECRET
                + "&code="+ WX_CODE
                + "&grant_type=authorization_code";

//        new Thread(new Runnable() 
//            @Override
//            public void run() {
//                String tokenResult = HttpUtil.httpsGet(accessTokenUrl);
//                if (null != tokenResult) {
//                    JSONObject tokenObj = JSON.parseObject(tokenResult);
//                    String accessToken = tokenObj.getString("access_token");
//                    String openId = tokenObj.getString("openid");
//                    String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
//                    String wxUserInfo = HttpUtil.httpsGet(userUrl);
//                    // JSONObject userObj = JSON.parseObject(wxUserInfo);
//                }
//            }
//        }).start();
        isWXLogin = false;
    }

}
