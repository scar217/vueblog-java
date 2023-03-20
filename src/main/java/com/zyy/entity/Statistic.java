package com.zyy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private Integer register;
    private Integer blogsNum;
    private Integer register_now;
    private Integer visitorNum;
}
