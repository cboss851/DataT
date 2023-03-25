package com.space.datat.service.database.sql;

import com.space.datat.service.database.enums.DatabaseTypeEnum;
import com.space.datat.service.database.sql.impl.GenerateSqlMysqlImpl;
import com.space.datat.service.database.sql.impl.GenerateSqlSqlServerImpl;

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
