package com.ruoyi.common.core.miniprogram;

import com.ruoyi.common.core.miniprogram.product.Product;

import java.io.Serializable;
import java.util.List;

import java.io.Serializable;
import java.util.List;

public class CategoricalDataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private List<Product> products;

    // 默认构造函数
    public CategoricalDataInfo() {
    }

    // 带参数构造函数
    public CategoricalDataInfo(Integer id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    // Getter 和 Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}


//public class CategoricalDataInfo implements Serializable {
//    private static final long serialVersionUID = 1L;
//    private List<?> products;
//    public CategoricalDataInfo()
//    {
//    }
//    public CategoricalDataInfo(List<?> list)
//    {
//        this.products = list;
//    }
//}
