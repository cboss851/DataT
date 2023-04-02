package com.tao.datat.service.database.sql;

import com.tao.datat.service.database.enums.DatabaseTypeEnum;
import com.tao.datat.service.database.sql.impl.GenerateSqlMysqlImpl;
import com.tao.datat.service.database.sql.impl.GenerateSqlSqlServerImpl;

public class GenerateSqlFactory {
    public static GenerateSql getInstance(DatabaseTypeEnum databaseType) {
        switch (databaseType) {
            case MySQL:
                return new GenerateSqlMysqlImpl();
            case SqlServer:
                return new GenerateSqlSqlServerImpl();
        }
        return null;
    }
}
