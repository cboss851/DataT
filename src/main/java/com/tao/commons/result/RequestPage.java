package com.tao.commons.result;


import com.tao.commons.result.enums.PageOrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分页请求
 */
@Data
public class RequestPage {
    @ApiModelProperty(value = "页码", example = "1", required = true)
    private Integer page = 1;

    @ApiModelProperty(value = "分页长度", example = "20", required = true)
    private Integer size = 20;

    @ApiModelProperty(value = "排序字段")
    private String orderField;

    @ApiModelProperty(value = "排序类型，DESC:升序,ASC:降序")
    private PageOrderTypeEnum orderType;
}