package com.gf.shardingjdbc.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author GF
 * @since 2023/4/1
 */
@Data
public class OrderVo {

    private String orderNo;
    private BigDecimal amount;

}
