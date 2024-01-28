package com.kirisaki.dutychain;

import com.kirisaki.pojo.BusinessLaunch;

import java.util.List;

/**
 * 抽象责任对象
 */
public abstract class AbstractBusinessHandler {
    public AbstractBusinessHandler nextHandler;

    public boolean hasNextHandler() {
        return this.nextHandler != null;
    }

    public abstract List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct);
}
