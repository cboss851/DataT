package com.tao.commons.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseResultPage<T> extends ResponseResult {
    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "当前页数据列表")
    private List<T> data;

    public ResponseResultPage(Long total, List<T> data) {
        this.setCode(ResponseCodeEnum.SUCCESS.getCode());
        this.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        this.total = total;
        this.data = data;
    }

    public static <T> ResponseResultPage<T> page(Long total, List<T> data) {
        return new ResponseResultPage(total, data);
    }
}