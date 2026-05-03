package org.example.aidemo.studyroom.controller;

import org.example.aidemo.common.model.Result;
import org.example.aidemo.studyroom.entity.Store;
import org.example.aidemo.studyroom.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class StoreController {
    @Autowired
    private StoreService StoreServiceImpl;
    @GetMapping("/getAllStores")
    public Result<List<Store>> getAllStores() {

        List<Store> stores = StoreServiceImpl.getAllStores();

        return Result.success(stores);
    }

}
