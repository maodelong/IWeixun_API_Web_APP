package com.delong.web.iweixun.utils;

import com.delong.web.iweixun.bean.api.base.PushModel;
import com.delong.web.iweixun.bean.db.User;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import java.io.IOException;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class PushDispatcher {
    public final static int TYPE_TRANS_MSG = 1;
    public final static int TYPE_LINK_MSG = 2;
    private static String appId = "W4RCIGF2qj5cGHPRu8ped3";
    private static String appKey = "OdOL7s3lyF7DuUrYKV2vt4";
    private static String masterSecret = "OrrTqYVzRY6AnoCw1Kz0n7";

    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private static PushDispatcher instance;
    private static IIGtPush push;
    private static IBatch mBatch;


    private PushDispatcher() {
        push = new IGtPush(host, appKey, masterSecret);
        mBatch = push.getBatch();
    }



    public static PushDispatcher getInstance() {
        if (instance == null) {
            synchronized (PushDispatcher.class) {
                if (instance == null) {
                    instance = new PushDispatcher();
                }
            }
        }
        return instance;
    }


    public PushDispatcher addPushMsg(User receiver, PushModel model, int type) {

        String pushStr = model.getPushString();
        switch (type) {
            case TYPE_LINK_MSG:
                try {
                    constructClientLinkMsg(receiver.getPushId(),pushStr,mBatch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_TRANS_MSG:
                try {
                    constructClientTransMsg(receiver.getPushId(),pushStr,mBatch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
        return this;
    }


    public void submit() {
        try {
            IPushResult result = mBatch.submit();
            String resultStr = result.toString();
            System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>"+resultStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void constructClientTransMsg(String cid, String msg, IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg);
        template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24*3600*1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }

    private static void constructClientLinkMsg(String cid, String msg, IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("title");
        template.setText("msg");
        template.setLogo("push.png");
        template.setLogoUrl("logoUrl");
        template.setUrl("url");

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24*3600*1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }
}
