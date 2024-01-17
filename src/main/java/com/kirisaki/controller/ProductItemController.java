package com.kirisaki.controller;

import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.pojo.ProductItem;
import com.kirisaki.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product")
@RestController
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    @GetMapping("/fetchAllItems")
    public ProductComposite findAll() {
        return productItemService.fetchAllItems();
    }

    @PostMapping("/addItems")
    public ProductComposite addItems(@RequestBody ProductItem item) {
        return productItemService.addItems(item);
    }
    @PostMapping("/delItems")
    public ProductComposite delItems(@RequestBody ProductItem item) {
        return productItemService.delItems(item);
    }
}
