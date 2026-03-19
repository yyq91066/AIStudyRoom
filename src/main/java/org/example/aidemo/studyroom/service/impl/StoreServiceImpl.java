package org.example.aidemo.studyroom.service.impl;

import org.example.aidemo.studyroom.entity.Store;
import org.example.aidemo.studyroom.mapper.StoreMapper;
import org.example.aidemo.studyroom.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;
    @Override
    public List<Store> getAllStores() {
        return storeMapper.getAllStores();
    }
}
