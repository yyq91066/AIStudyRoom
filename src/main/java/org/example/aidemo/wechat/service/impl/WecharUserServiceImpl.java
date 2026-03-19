package org.example.aidemo.wechat.service.impl;

import org.example.aidemo.common.util.HttpClientUtil;
import org.example.aidemo.wechat.DTO.WechatLoginDTO;
import org.example.aidemo.wechat.exception.LoginFailedException;
import org.example.aidemo.wechat.mapper.WxUserMapper;
import org.example.aidemo.wechat.model.WxUser;
import org.example.aidemo.wechat.service.WechatUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WecharUserServiceImpl implements WechatUserService {
    @Autowired
    private WxUserMapper wxUserMapper;

    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Override
    public WxUser wxLogin(WechatLoginDTO wechatLoginDTO) {
        String code = wechatLoginDTO.getCode();
        if (code == null && code.equals("")) {
            throw new LoginFailedException("登陆失败：未获取到code");
        }

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("appid","wx08c28ddd9c8a0f3a");
        paramMap.put("secret","c33ca08b79ff67576e9719cbab9d280b");
        paramMap.put("js_code",code);
        paramMap.put("grant_type","authorization_code");
        String res = HttpClientUtil.doGet(WX_LOGIN, paramMap);



        JSONObject jsonObject = new JSONObject(res);
//        JSONObject jsonObject = JSON.parseObject(res);
        String openid = jsonObject.getString("openid");

        if (openid == null && openid.equals("")) {
            throw new LoginFailedException("openid不存在");
        }

        WxUser userByOpenId = wxUserMapper.getUserByOpenId(openid);
        if (userByOpenId == null) {
             userByOpenId = WxUser.builder().openid(openid).createTime(LocalDateTime.now()).build();
            wxUserMapper.insertWxUser(userByOpenId);
        }
        return userByOpenId;
    }
}
