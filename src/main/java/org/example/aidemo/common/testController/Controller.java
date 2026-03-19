package org.example.aidemo.common.testController;

import org.example.aidemo.aotuFillTest.controller.TestDTO;
import org.example.aidemo.common.model.Result;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/test")
public class Controller {
    @GetMapping("/get/hello")
    public Result<String> hello() {
        //获取当前时间
        LocalDateTime now1 = LocalDateTime.now();
        String now = LocalDateTime.now().toString();
        //将当前时间2026-02-06T16:12:11.545515后面的截取掉
        int lastDotIndex = now.lastIndexOf('.');
        String substring = now.substring(0, lastDotIndex);

        //日期格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String formatted = now1.format(formatter);

        return Result.success("hello 当前时间："+formatted);
    }
    @PostMapping("/post/hello")
    public Result<String> helloPost(@RequestBody TestDTO testDTO) {
        return Result.success("helloPost"+testDTO.getName());
    }
}
