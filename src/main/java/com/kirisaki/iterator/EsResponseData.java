package com.kirisaki.iterator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 接收 Es 的返回值
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsResponseData {
    // 字段名以及字段类型
    private List<Map<String, String>> columns;
    // 返回的数据
    private List<List<Object>> rows;
    // 是否还存在下一页的数据
    private String cursor;
}
