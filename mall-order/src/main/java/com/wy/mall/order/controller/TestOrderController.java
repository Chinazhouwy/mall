package com.wy.mall.order.controller;

import com.wy.common.utils.R;
import com.wy.mall.order.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/test")
@RefreshScope
public class TestOrderController {


    @Autowired
    ProductFeignService productFeignService;

    @Value("${testName}")
    private String testName;


    @RequestMapping("/product/list")
    public R membercoupons(){    //全系统的所有返回都返回R
        // 模拟去数据库查用户对于的优惠券
        R orderProductList = productFeignService.orderProductList();
        return R.ok().put("testName", testName).put("productList", orderProductList.get("productList"));
    }


}
