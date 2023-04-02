package com.tao.datat.service.database.sql;

import com.tao.datat.service.database.Table;

/**
 * 生成sql语句
 */
public interface GenerateSql {
    //插入sql
    String insert(Table table);

    //修改sql
    String update(Table table);

    //表总数量sql
    String count(Table table);

    //查询前面多少条数据
    String limit(Table table,Long limit);


}
