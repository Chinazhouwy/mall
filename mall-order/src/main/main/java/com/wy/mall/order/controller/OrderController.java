package com.wy.mall.order.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wy.mall.order.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wy.mall.order.entity.OrderEntity;
import com.wy.mall.order.service.OrderService;
import com.wy.common.utils.PageUtils;
import com.wy.common.utils.R;



/**
 * 订单
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:00:50
 */
@RestController
@RequestMapping("order/order")
@RefreshScope
public class OrderController {
    @Autowired
    private OrderService orderService;

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


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:order:info")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:order:save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:order:delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
