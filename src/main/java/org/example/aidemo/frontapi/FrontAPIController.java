package org.example.aidemo.frontapi;

import org.example.aidemo.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/front")
@CrossOrigin
public class FrontAPIController {
    @Autowired
    private FrontService frontService;
    @GetMapping("/head/category")
    @Cacheable(cacheNames = "categoryCache", key = "'category'")
    public Result<List<FrontCategory>> getCategory(){

        return Result.success(frontService.getCategory());
    }
}
