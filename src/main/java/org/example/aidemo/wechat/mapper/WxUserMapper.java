package org.example.aidemo.wechat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.aidemo.wechat.model.WxUser;

@Mapper
public interface WxUserMapper {
    @Select("select * from wx_user where openid=#{openid}")
    WxUser getUserByOpenId(String openid);

    void insertWxUser(WxUser wxUser);
}
