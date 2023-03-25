package com.space.datat.service.databaseinfo.dto;

import com.space.datat.service.database.DataSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TableCountListReq {
    @ApiModelProperty("数据源")
    DataSource ds;

    @ApiModelProperty("模式")
    private String schema;

    @ApiModelProperty("表名")
    private String name;
}