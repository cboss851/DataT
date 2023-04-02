package com.tao.datat.service.transfer.dto;

import com.tao.datat.service.database.DataSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferReq {
    @ApiModelProperty("数据源")
    DataSource sourceDS;

    @ApiModelProperty("数据目标")
    DataSource objectDS;

    @ApiModelProperty("表名")
    List<TableReq> tables;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class TableReq {
        @ApiModelProperty("表名")
        String name;

        @ApiModelProperty(example = "100000", name = "数量")
        Long count;
    }
}