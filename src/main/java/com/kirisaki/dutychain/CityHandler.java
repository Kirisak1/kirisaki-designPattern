package com.kirisaki.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.kirisaki.pojo.BusinessLaunch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 筛选相关城市的业务投放数据
 */
public class CityHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        //复用变量
        launchList = launchList.stream().filter(launch -> {
            String city = launch.getTargetCity();
            if (StringUtils.isEmpty(city)) {
                return true;
            }
            List<String> cityList = Arrays.asList(city.split(","));
            return cityList.contains(targetCity);
        }).collect(Collectors.toList());
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
