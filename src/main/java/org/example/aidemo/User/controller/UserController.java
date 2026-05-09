package org.example.aidemo.User.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.aidemo.User.dto.RegisterDTO;
import org.example.aidemo.User.model.User;
import org.example.aidemo.User.service.UserService;
import org.example.aidemo.User.vo.RegisterVO;
import org.example.aidemo.common.model.Result;
import org.example.aidemo.common.util.JwtUtil;
import org.example.aidemo.common.util.Md5Util;
import org.example.aidemo.common.util.ThreadLocalUtil;
import org.example.aidemo.common.util.VerifyCodeUtils;
import org.example.aidemo.file.entiy.SystemFile;
import org.example.aidemo.file.service.SystemFIleService;
import org.example.aidemo.mail.service.EmailService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate1;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/register")
//    public Result register(
//                           @NotBlank(message = "用户名不能为空") @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "用户名格式错误")
//                           String username,
//                           String password,
//                           @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式错误")
//                           String mail,
//                           @NotBlank(message = "验证码不能为空") @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "请正确填写验证码")
//                           String code)
    public Result register(@RequestBody @Validated RegisterDTO registerDTO)
    {
        if (registerDTO.getUsername() == null || registerDTO.getUsername().isEmpty()||registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            return Result.error("用户名或密码不能为空");
        }
        User byUsername = userService.findByUsername(registerDTO.getUsername());
        if (byUsername != null) {
            return Result.error(registerDTO.getUsername()+"用户名已存在");
        }

        // 3. 校验邮箱验证码（核心步骤）
        String redisKey = "register_code_" + registerDTO.getEmail();
        String cacheCode = redisTemplate1.opsForValue().get(redisKey);
        if (cacheCode == null) {
            return Result.error("验证码已过期，请重新获取");
        }
        if (!cacheCode.equals(registerDTO.getCode())) {
            return Result.error("验证码错误，请核对后重试");
        }

        // 4. 验证码校验通过，删除缓存（防止重复使用）
        redisTemplate1.delete(redisKey);

        User user =new User();
        //昵称
        if (registerDTO.getNickname() == null || registerDTO.getNickname().isEmpty()) {
            user.setNickname("用户");
        }
        else {
            user.setNickname(registerDTO.getNickname());
        }
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        userService.register(user, registerDTO.getCode());

        return Result.success(registerDTO.getUsername()+"注册成功");
    }
    @GetMapping("/send-register-code")
    public Result sendRegisterCode(@RequestParam String email) {
        // 1. 校验邮箱格式（也可加注解）
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        if (!email.matches(regex)) {
            return Result.error("邮箱格式错误");
        }

        // 2. 生成6位验证码
        String code = VerifyCodeUtils.generateCode();

        // 3. 缓存验证码到Redis（key: register_code_邮箱，过期时间5分钟）
        String redisKey = "register_code_" + email;
        // 防止频繁发送：如果已存在验证码，提示1分钟后重试
        if (redisTemplate1.hasKey(redisKey)) {
            return Result.error("验证码已发送，请勿重复发送（60秒内有效）");
        }
        redisTemplate1.opsForValue().set(redisKey, code, 60, TimeUnit.SECONDS);

        // 4. 发送验证码到邮箱
        try {
            emailService.sendSimpleEmail(email, "注册验证码", code);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证码发送失败，请稍后重试");
        }

        return Result.success("验证码已发送至你的邮箱，1分钟内有效");
    }

    @PostMapping("/login")
    public Result<RegisterVO> login(
//            @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "用户名格式错误") String username,
//            @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "密码格式错误")  String password,
            @RequestBody @Validated RegisterDTO registerDTO,
                        HttpServletRequest request
                        )
    {


        String ip = getRequestIp(request);
        if (registerDTO.getUsername() == null || registerDTO.getUsername().isEmpty()||registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            return Result.error("用户名或密码不能为空");
        }
        User byUsername = userService.findByUsername(registerDTO.getUsername());
        if (byUsername == null) {
            return Result.error(registerDTO.getUsername()+"用户名错误");
        }
        if (byUsername.getPassword().trim().equals(Md5Util.getMD5String(registerDTO.getPassword()))) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", byUsername.getUsername());
            claims.put("id",byUsername.getId() );
            String jwt = JwtUtil.genToken(claims);
            //把token存到redis
            ValueOperations<String, String> stringStringValueOperations = redisTemplate1.opsForValue();
            stringStringValueOperations.set(jwt, jwt,24, TimeUnit.HOURS);
            RegisterVO registerVO = new RegisterVO();
            registerVO.setToken(jwt);
            registerVO.setUser(byUsername);

            return Result.success(registerVO);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/UserInfo")
//    public Result UserInfo(@RequestHeader(name="Authorization") String token) {
    public Result UserInfo() {
//        Map<String, Object> claims = JwtUtil.parseToken(token);
//            String username = (String) claims.get("username");
            Map<String, Object> claims = ThreadLocalUtil.get();
            User user = userService.findByUsername((String) claims.get("username"));

            return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        Map<String, Object> claims = ThreadLocalUtil.get();
        user.setId((Integer) claims.get("id"));
        userService.update(user);
        return Result.success(user);

    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);

        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String>  params,@RequestHeader("Authorization") String token){
        //1校验
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");
        if (oldPwd == null || oldPwd.isEmpty()||newPwd == null || newPwd.isEmpty()||rePwd == null || rePwd.isEmpty()) {
            return Result.error("密码不能为空");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("旧密码错误");
        }
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码不一致");
        }
        userService.updatePwd(newPwd);
        redisTemplate1.opsForValue().getOperations().delete(token);

        return Result.success();
    }

    private String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多代理场景（如X-Forwarded-For: 192.168.1.1, 10.0.0.1）
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;}


/**
 * 用户头像更换
 */
    @Autowired
    private SystemFIleService systemFIleService;
    @PostMapping(value = "/updateAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateAvatarAndFile(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute SystemFile systemFile
    ) {
        try {
            String url=systemFIleService.uploadFile(file, systemFile);
            userService.updateAvatar(url);
            return Result.success(url);
        }catch (Exception e)
            {
            return Result.error("上传失败");
        }

    }
}
