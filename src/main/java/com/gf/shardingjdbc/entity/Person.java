package com.gf.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author GF
 * @since 2023/3/31
 */
@TableName("t_person")
@Data
public class Person {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

}
