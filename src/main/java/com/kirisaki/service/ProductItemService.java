package com.kirisaki.service;


import com.kirisaki.items.composite.AbstractProductItem;
import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.pojo.ProductItem;
import com.kirisaki.repo.ProductItemRepository;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    /**
     * 查询所有商品类目信息
     *
     * @return
     */
    public ProductComposite fetchAllItems() {
        Object cacheItems = redisCommonProcessor.get("items");
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }
        List<ProductItem> fetchDbItems = productItemRepository.findAll();
        //将DB中的商品类目信息拼装成组合牧师的属性结构
        ProductComposite items = generateProductTree(fetchDbItems);
        if (items == null) {
            throw new UnsupportedOperationException("Product items should not be empty in DB!");
        }
        redisCommonProcessor.set("items", items);
        return items;
    }

    /**
     * 拼装组合模式
     *
     * @param fetchDbItems
     * @return
     */
    private ProductComposite generateProductTree(List<ProductItem> fetchDbItems) {
        // 将所有的商品类目都转换成组合模式的子类
        ArrayList<ProductComposite> composites = new ArrayList<>(fetchDbItems.size());
        fetchDbItems.forEach(dbItem -> composites.add(ProductComposite.builder()
                .id(dbItem.getId())
                .name(dbItem.getName())
                .pid(dbItem.getPid())
                .build()));
        //将父类相同的子类提取出来
        Map<Integer, List<ProductComposite>> groupingList = composites.stream().collect(Collectors.groupingBy(ProductComposite::getPid));
        //然后将相同父类代号的子类合并到父类中childList中
        composites.stream().forEach(item -> {
            List<ProductComposite> list = groupingList.get(item.getId());
            item.setChild(
                    list == null ? new ArrayList<>() : list.stream().map(x -> (AbstractProductItem) x).collect(Collectors.toList())
            );
        });
        //get(0)  可能需要修改
        ProductComposite composite = composites.size() == 0 ? null : composites.get(0);
        return composite;
    }
}
