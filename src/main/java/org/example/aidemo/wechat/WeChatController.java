package org.example.aidemo.wechat;

import lombok.extern.slf4j.Slf4j;
import org.example.aidemo.common.model.Result;
import org.example.aidemo.common.util.JwtUtil;
import org.example.aidemo.wechat.DTO.WechatLoginDTO;
import org.example.aidemo.wechat.VO.WechatLoginVO;
import org.example.aidemo.wechat.model.WxUser;
import org.example.aidemo.wechat.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wechat/user")
@Slf4j
public class WeChatController {

    @Autowired
    private WechatUserService wechatUserService;
    @PostMapping("/login")
    public Result<WechatLoginVO> login(@RequestBody WechatLoginDTO wechatLoginDTO){
        log.info("微信登录开始"+wechatLoginDTO.getCode());
        WxUser wxuser =wechatUserService.wxLogin(wechatLoginDTO);

        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id",wxuser.getId());
        String token = JwtUtil.genToken(claims);
        WechatLoginVO wechatLoginVO = WechatLoginVO.builder()
                .id(wxuser.getId())
                .openid(wxuser.getOpenid())
                .token(token)
                .build();
        return Result.success(wechatLoginVO);
    }
}
