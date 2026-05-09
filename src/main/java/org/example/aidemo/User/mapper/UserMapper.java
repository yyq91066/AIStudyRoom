package org.example.aidemo.User.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.aidemo.User.model.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user_entity(username,nickname,password,email,create_time,update_time) values(#{username},#{nickname},#{password},#{email},now(),now())")
    void register(User user);

    @Select("select * from user_entity where username=#{username}")
    User findByUsername(String username);

    @Update("update user_entity set nickname=#{nickname},email=#{email},update_time=now(),phone=#{phone} where id = #{id}")
    void update(User user);

    @Update("update user_entity set user_pic= #{avatarUrl},update_time=now() where id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user_entity set password= #{md5String},update_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
