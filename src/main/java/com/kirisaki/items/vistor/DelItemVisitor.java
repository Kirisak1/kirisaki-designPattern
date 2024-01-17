package com.kirisaki.items.vistor;

import com.kirisaki.items.composite.AbstractProductItem;
import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelItemVisitor implements ItemVisitor<AbstractProductItem> {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        //获取数据集
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        //删除
        ProductComposite delItem = (ProductComposite) productItem;
        if (delItem.getId() == currentItem.getId()) {
            throw new UnsupportedOperationException("根节点不能删");
        }
        if (delItem.getPid() == currentItem.getId()) {
            currentItem.delProductChild(delItem);
            return currentItem;
        }
        delChild(delItem, currentItem);
        return currentItem;
    }

    /**
     * 递归遍历删除
     *
     * @param currentItem
     * @param productItem
     */
    private void delChild(ProductComposite productItem, ProductComposite currentItem) {
        for (AbstractProductItem abstractItem : currentItem.getChild()) {
            ProductComposite item = (ProductComposite) abstractItem;
            if (item.getId() == productItem.getPid()) {
                item.delProductChild(productItem);
                break;
            } else {
                delChild(item, productItem);
            }
        }
    }
}
