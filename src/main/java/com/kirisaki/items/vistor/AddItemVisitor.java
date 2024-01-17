package com.kirisaki.items.vistor;

import com.kirisaki.items.composite.AbstractProductItem;
import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddItemVisitor implements ItemVisitor<AbstractProductItem> {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        ProductComposite addItem = (ProductComposite) productItem;
        //如果待添加商品是根节点的子类则直接添加
        if (addItem.getPid() == currentItem.getId()) {
            currentItem.addProductItem(addItem);
            return currentItem;
        }
        //如果不是则循环添加
        addChild(addItem, currentItem);
        return currentItem;
    }

    /**
     * 给商品类目结构添加商品
     *
     * @param currentItem 需要遍历添加的树结构
     * @param addItem     待添加的商品类目
     * @return
     */
    private void addChild(ProductComposite addItem, ProductComposite currentItem) {
        //遍历子类, 如果
        for (AbstractProductItem abstractItem : currentItem.getChild()) {
            ProductComposite item = (ProductComposite) abstractItem;
            if (item.getId() == addItem.getPid()) {
                item.addProductItem(addItem);
                break;
            } else {
                addChild(addItem, item);
            }
        }
    }
}
