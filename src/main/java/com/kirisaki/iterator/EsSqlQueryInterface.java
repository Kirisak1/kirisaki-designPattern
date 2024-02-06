package com.kirisaki.iterator;

/**
 * 抽象容器类
 */
public interface EsSqlQueryInterface<T> {
    /**
     * 获取迭代器对象的方法
     */
    public T iterator();
}
