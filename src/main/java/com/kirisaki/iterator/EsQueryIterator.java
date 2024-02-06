package com.kirisaki.iterator;

import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 迭代器类, 用于处理 ES 的返回值
 */
public class EsQueryIterator implements Iterator<Map<String, Object>> {
    //记录当前 cursor 分页
    private String cursor;
    //记录查询的 columns 因为只有第一次查询才会返回 columns 数据
    private List<String> columns;
    //将 ES SQL Rest API 的返回值封装到 List<Map> 中,以方便处理返回值
    private Iterator<Map<String, Object>> iterator;
    RestTemplate restTemplate = new RestTemplate();

    //构造函数进行第一次查询, 并且初始化我们后续需要使用的 columns丶iterator 和 cursor
    public EsQueryIterator(String query, Long fetchSize) {
        EsResponseData responseData = restTemplate.postForObject("http://localhost:9200/_sql?format=json", new EsSqlQuery(query, fetchSize), EsResponseData.class);
        this.cursor = responseData.getCursor();
        this.columns = responseData.getColumns().stream().map(x -> x.get("name")).collect(Collectors.toList());
        this.iterator = convert(columns, responseData).iterator();
    }


    @Override
    public boolean hasNext() {
        return iterator.hasNext() || scrollNext();
    }

    private boolean scrollNext() {
        if (iterator == null || this.cursor == null) {
            return false;
        }
        EsResponseData responseData = restTemplate.postForObject("http://localhost:9200/_sql?format=json", new EsSqlQuery(this.cursor), EsResponseData.class);
        this.cursor = responseData.getCursor();
        this.iterator = convert(columns, responseData).iterator();
        //继续进行迭代
        return iterator.hasNext();
    }

    @Override
    public Map<String, Object> next() {
        return iterator.next();
    }

    //将 ES SQL Rest API 的返回值转化为 List<Map>
    private List<Map<String, Object>> convert(List<String> columns, EsResponseData responseData) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (List<Object> row : responseData.getRows()) {
            HashMap<String, Object> map = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                map.put(columns.get(i), row.get(i));
            }
            results.add(map);
        }
        return results;
    }
}
