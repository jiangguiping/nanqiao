package com.qf.nanqiao.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.qf.nanqiao.common.OrderState;
import com.qf.nanqiao.common.PayType;
import com.qf.nanqiao.entity.Orders;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Version: V1.0
 */
@Slf4j
@RestController
@Api(tags = "支付接口")
@RequestMapping("pay")
@RequiredArgsConstructor
public class EasyPayController {

    public static final String ALIPAY_TRADE_PRECREATE_RESPONSE = "alipay_trade_precreate_response";
    public static final String CODE = "code";
    public static final String QR_CODE = "qr_code";
    private final OrdersService ordersService;

    /**
     * 支付
     *
     * @param code 订单编号
     * @return {@link String}
     */
    @GetMapping("aliPay/{code}")
    @ApiImplicitParam(name = "code", value = "订单编号", required = true)
    @ApiOperation("支付接口")
    public R<String> pay(@PathVariable String code) {
        try {
            // 校验订单编号不能为空
            Assert.hasText(code, "订单编号不能为空");
            // 根据订单编号查询订单信息
            Orders orders = ordersService.queryByNo(code);
            if (Objects.nonNull(orders)) {
                // 如果订单的状态是未付款的，才发起付款
                if (orders.getFlag() == OrderState.UNPAID.getCode()) {
                    // 继续支付
                    // 2. 发起API调用（以创建当面付收款二维码为例）
                    String subject = orders.getProjectName();
                    String totalMoney = orders.getTotalMoney() + "";
                    log.info("发起支付,订单号{},订单的总金额{}", code, totalMoney);
                    // 参数： subject: 商品信息 outTradeNo: 订单编号 totalAmount: 支付总金额
                    AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(subject, code, totalMoney);
                    // 3. 处理响应或异常
                    if (ResponseChecker.success(response)) {
                        log.info("支付API调用成功");
                        Map map = JSON.parseObject(response.getHttpBody(), Map.class);
                        if (Objects.nonNull(map)) {
                            Map map2 = JSON.parseObject(map.get(ALIPAY_TRADE_PRECREATE_RESPONSE).toString(), Map.class);
                            if (Objects.nonNull(map2)) {
                                String code1 = map2.get(CODE).toString();
                                if (Objects.equals(code1, "10000")) {
                                    log.info("发起支付成功");
                                    return R.ok(map2.get(QR_CODE).toString());
                                }
                            }
                        }
                    } else {
                        log.error("调用失败，原因：" + response.toString());
                    }
                } else {
                    return R.fail("订单已支付，无需重复付款");
                }
            } else {
                return R.fail("订单不存在");
            }
        } catch (Exception e) {
            log.error("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return R.fail("支付失败");
    }


    /**
     * 查询支付状态
     *
     * @param code 订单编号
     * @return {@link String}
     */
    @GetMapping("queryPay/{code}")
    @ApiImplicitParam(name = "code", value = "订单编号", required = true)
    @ApiOperation("查询订单的支付状态")
    public R<String> queryPay(@PathVariable String code) {
        try {
            Assert.hasText(code, "订单编号不能为空");
            // 通用能力中的一个接口 查询支付状态
            AlipayTradeQueryResponse response = Factory.Payment.Common().query(code);

            Map<String, Map<String, String>> map = JSON.parseObject(response.getHttpBody(), Map.class);
            String s = map.get("alipay_trade_query_response").get("trade_status");
            if (Objects.isNull(s)) {
                return R.fail("交易不存在");
            }
            if (s.equals("TRADE_SUCCESS")) {
                log.info("支付成功......");
                log.info("修改订单状态.....");
                if (ordersService.payOrder(code)) {
                    return R.ok("支付成功");
                }
            } else if (s.equals("WAIT_BUYER_PAY")) {
                return R.ok("等待买家付款");
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return R.fail("支付失败");
    }
}
