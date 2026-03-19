package org.example.aidemo.User.service.serviceImpl;

import org.example.aidemo.User.mapper.UserMapper;
import org.example.aidemo.User.model.User;
import org.example.aidemo.User.service.UserService;
import org.example.aidemo.common.util.Md5Util;
import org.example.aidemo.common.util.ThreadLocalUtil;
import org.example.aidemo.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User byUsername = userMapper.findByUsername(username);
        return byUsername;
    }

    @Autowired
    private EmailService emailService;
    @Override
    public void register(User user, String code) {
        user.setPassword(Md5Util.getMD5String(user.getPassword()));
        userMapper.register(user);
        // . 发送注册信息到邮箱
        try {
            emailService.sendSimpleEmail(user.getEmail(),
                    "账户注册成功",
                    "您的用户名："+user.getUsername()+"\n"+"创建时间："+ LocalDateTime.now() +"\n"+"绑定邮箱为："+user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
