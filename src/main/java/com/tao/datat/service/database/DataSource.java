package com.tao.datat.service.database;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSource {
    @ApiModelProperty(value = "",example = "com.microsoft.sqlserver.jdbc.SQLServerDriver",required = true)
    private String driverClassName;

    @ApiModelProperty(value = "URL",example = "jdbc:sqlserver://localhost:1433;database=CQP_DATAFLOW_OBJECT",required = true)
    private String url;

    @ApiModelProperty(value = "登录名",example = "sa",required = true)
    private String username;

    @ApiModelProperty(value = "密码",example = "",required = true)
    private String password;
}