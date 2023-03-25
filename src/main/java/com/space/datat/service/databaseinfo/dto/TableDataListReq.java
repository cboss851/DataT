package com.space.datat.service.databaseinfo.dto;

import com.space.datat.service.database.DataSource;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDataListReq {
    @ApiModelProperty("数据源")
    DataSource ds;

    @ApiModelProperty("sql")
    private String sql;
}