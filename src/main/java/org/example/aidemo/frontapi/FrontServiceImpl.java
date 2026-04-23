package org.example.aidemo.frontapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontServiceImpl implements FrontService{

    @Autowired
    private FrontCateMapper frontCategoryMapper;
    @Override
    public List<FrontCategory> getCategory() {
        return frontCategoryMapper.getCategory();
    }
}
