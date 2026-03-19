package org.example.aidemo.wechat.service;

import org.example.aidemo.wechat.DTO.WechatLoginDTO;
import org.example.aidemo.wechat.model.WxUser;

public interface WechatUserService {
    WxUser wxLogin(WechatLoginDTO wechatLoginDTO);
}
