package com.kirisaki.items.vistor;

import com.kirisaki.items.composite.AbstractProductItem;

public interface ItemVisitor<T> {
    T visitor(AbstractProductItem productItem);
}
