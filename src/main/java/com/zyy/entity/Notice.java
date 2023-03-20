package com.zyy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_notice")
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "标题不能为空!")
    private String title;
    @NotBlank(message = "公告内容不能为空!")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;

}
