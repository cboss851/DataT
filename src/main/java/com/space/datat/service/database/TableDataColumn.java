package com.space.datat.service.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDataColumn {
    private TableField field;
    private Object value;
}
