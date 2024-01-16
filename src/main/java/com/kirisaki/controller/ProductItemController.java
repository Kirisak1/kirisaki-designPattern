package com.kirisaki.controller;

import com.kirisaki.items.composite.ProductComposite;
import com.kirisaki.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@RestController
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    @GetMapping("/fetchAllItems")
    public ProductComposite findAll() {
        return productItemService.fetchAllItems();
    }
}
