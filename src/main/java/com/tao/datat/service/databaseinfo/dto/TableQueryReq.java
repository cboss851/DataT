package com.tao.datat.service.databaseinfo.dto;

import com.tao.datat.service.database.DataSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TableQueryReq {
    @ApiModelProperty("数据源")
    DataSource ds;

    @ApiModelProperty("表名")
    private String name;

    @ApiModelProperty("模式")
    private String schema;
}