package org.example.aidemo.User.service;


import org.example.aidemo.User.model.User;

public interface UserService {
    User findByUsername(String username);

    void register(User user,String code);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

}
