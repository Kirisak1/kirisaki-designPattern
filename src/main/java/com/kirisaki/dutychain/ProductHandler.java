package com.kirisaki.dutychain;

import com.kirisaki.pojo.BusinessLaunch;

import java.util.List;

public class ProductHandler extends AbstractBusinessHandler{
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        return launchList;
    }
}
