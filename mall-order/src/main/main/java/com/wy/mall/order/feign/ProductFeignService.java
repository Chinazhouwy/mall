package com.wy.mall.order.feign;


import com.wy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-product")
public interface ProductFeignService {

    // 远程服务的url
    @RequestMapping("product/attr/test/list")
    public R orderProductList();//得到一个R对象

}
