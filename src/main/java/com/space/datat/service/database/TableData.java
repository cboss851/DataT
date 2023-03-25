package com.space.datat.service.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableData {
    //表
    private Table table;
    //一行数据
    private List<List<TableDataColumn>> rows;
}