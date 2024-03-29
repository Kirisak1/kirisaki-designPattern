package com.kirisaki.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.kirisaki.pojo.BusinessLaunch;

import java.util.List;
import java.util.stream.Collectors;

public class SexHandler extends AbstractBusinessHandler{
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        //复用变量
        launchList = launchList.stream().filter(launch -> {
            String sex = launch.getTargetSex();
            if (StringUtils.isEmpty(sex)) {
                return true;
            }
            //性别是单选,不需要再分割了
            return sex.equals(targetSex);
        }).collect(Collectors.toList());
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
