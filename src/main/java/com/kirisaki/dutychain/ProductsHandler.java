package com.kirisaki.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.kirisaki.pojo.BusinessLaunch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsHandler extends AbstractBusinessHandler{
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        //复用变量
        launchList = launchList.stream().filter(launch -> {
            String product = launch.getTargetProduct();
            if (StringUtils.isEmpty(product)) {
                return true;
            }
            List<String> productList = Arrays.asList(product.split(","));
            return productList.contains(targetProduct);
        }).collect(Collectors.toList());
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
