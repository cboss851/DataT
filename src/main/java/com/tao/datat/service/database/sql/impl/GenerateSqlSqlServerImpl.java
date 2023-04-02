package com.tao.datat.service.database.sql.impl;

import com.tao.datat.service.database.Table;
import com.tao.datat.service.database.TableField;
import com.tao.datat.service.database.sql.GenerateSql;

/**
 * SqlServer的SQL
 */
public class GenerateSqlSqlServerImpl implements GenerateSql {

    @Override
    public String insert(Table table) {
        String fieldK = "";
        String fieldV = "";
        for (TableField field : table.getFields()) {
            fieldK += ("".equals(fieldK) ? "" : ",") + field.getName();
            fieldV += ("".equals(fieldV) ? "" : ",") + "?";
        }
        String strFullSql = "INSERT INTO " + table.getName() + "(" + fieldK + ") VALUES (" + fieldV + ")";
        return strFullSql;
    }

    @Override
    public String update(Table table) {
        return null;
    }

    @Override
    public String count(Table table) {
        String sql = "select count(1) num from \"" + table.getName() + "\"";
        return sql;
    }

    @Override
    public String limit(Table table, Long limit) {
        String sql = "select top " + limit + " * from \"" + table.getName() + "\"";
        return sql;
    }
}
