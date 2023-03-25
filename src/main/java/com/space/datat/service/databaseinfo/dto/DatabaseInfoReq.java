package com.space.datat.service.databaseinfo.dto;

import com.space.datat.service.database.DataSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DatabaseInfoReq {
    @ApiModelProperty("数据源")
    DataSource ds;
}
