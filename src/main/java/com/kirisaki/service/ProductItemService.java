package com.kirisaki.service;


import com.kirisaki.items.composite.AbstractProductItem;
import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.items.vistor.AddItemVisitor;
import com.kirisaki.items.vistor.DelItemVisitor;
import com.kirisaki.pojo.ProductItem;
import com.kirisaki.repo.ProductItemRepository;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    @Autowired
    private AddItemVisitor addItemVisitor;
    @Autowired
    private DelItemVisitor delItemVisitor;

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

    /**
     * 新增善品类目
     *
     * @param item
     * @return
     */
    public ProductComposite addItems(ProductItem item) {

        productItemRepository.addItem(item.getName(), item.getPid());

        //修改缓存
        ProductComposite addItem = ProductComposite.builder()
                .id(productItemRepository.findByNameAndPid(item.getName(), item.getPid()).getId())
                .name(item.getName())
                .pid(item.getPid())
                .child(new ArrayList<>())
                .build();
        AbstractProductItem updatedItems = addItemVisitor.visitor(addItem);
//此处可以增加重试机制, 可以使用spring-retry 或者消息队列
        redisCommonProcessor.set("items", updatedItems);
        return (ProductComposite) updatedItems;
    }

    /**
     * 删除商品类目
     *
     * @param item
     * @return
     */
    public ProductComposite delItems(ProductItem item) {
        productItemRepository.delItem(item.getId());
        //修改缓存
        ProductComposite delItem = ProductComposite.builder()
                .id(item.getId())
                .name(item.getName())
                .pid(item.getPid())
                .child(new ArrayList<>())
                .build();
        AbstractProductItem updatedItems = delItemVisitor.visitor(delItem);

        redisCommonProcessor.set("items", updatedItems);
        return (ProductComposite) updatedItems;
    }
}
