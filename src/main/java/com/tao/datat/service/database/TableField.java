package com.tao.datat.service.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableField {
    private String name;//列名
    private int dataType;//对应的java.sql.Types的SQL类型(列类型ID)
    private String dataTypeName;//java.sql.Types类型名称(列类型名称)
    private int columnSize;//列大小
    private String comment;
    private int length;//长度
    private String isNull;//是否允许空
    private String defaultValue;//默认值
}
