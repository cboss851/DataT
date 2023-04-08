package com.tao.datat.service.database;

import com.tao.datat.service.database.enums.DatabaseTypeEnum;
import com.tao.datat.service.database.sql.GenerateSqlFactory;
import com.tao.datat.service.database.utils.JdbcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据库元数据操作
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseMetaService {
    //表缓存
    private HashMap<String, Table> tableCached = new HashMap<>();
    //Database缓存
    private HashMap<String, Database> databaseCached = new HashMap<>();

    public List<TableField> getTableFields(Table table, Connection connection, DatabaseMetaData databaseMetaData) throws SQLException {
        List<TableField> listTableFields = new ArrayList<>();
        //指定表元数据信息
        ResultSet rs = databaseMetaData.getColumns(connection.getCatalog(), null, table.getName(), null);
        while (rs.next()) {
            TableField tableField = TableField.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .dataType(rs.getInt("DATA_TYPE"))
                    .dataTypeName(rs.getString("TYPE_NAME"))
                    .columnSize(rs.getInt("COLUMN_SIZE"))
                    .comment(rs.getString("REMARKS"))
                    .length(rs.getInt("COLUMN_SIZE"))
                    .isNull(rs.getString("IS_NULLABLE"))
                    .defaultValue(rs.getString("COLUMN_DEF"))
                    .build();
            listTableFields.add(tableField);
        }
        return listTableFields;
    }

    /**
     * 查询表数据
     *
     * @param table
     * @param ds
     * @param limit
     * @return
     */
    public TableData getTableData(Table table, DataSource ds, Long limit) throws SQLException {
        Connection connection = null;
        try {
            //装载驱动类
            Class.forName(ds.getDriverClassName());
            //先获取jdbc连接
            connection = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());

            Statement stmt = connection.createStatement();
            String sql = GenerateSqlFactory
                    .getInstance(table.getDatabase().getType())
                    .limit(table, limit);
            ResultSet rs = stmt.executeQuery(sql);
            List<List<TableDataColumn>> rows = new ArrayList<>();
            while (rs.next()) {
                List<TableDataColumn> row = new ArrayList<>();
                for (TableField field : table.getFields()) {
                    row.add(TableDataColumn.builder()
                            .field(field)
                            .value(rs.getObject(field.getName()))
                            .build());
                }
                rows.add(row);
            }
            return TableData.builder()
                    .table(table)
                    .rows(rows)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConn(connection);
        }
        return null;
    }

    /**
     * 查询表元数据信息
     *
     * @param ds
     * @param tableName
     * @return
     */
    public Table getTable(DataSource ds, String schema, String tableName) throws SQLException {
        if (tableCached.containsKey(tableName)) {
            return tableCached.get(tableName);
        } else {
            Table table = getTables(ds, schema, tableName).get(0);
            tableCached.put(tableName, table);
            return table;
        }
    }

    /**
     * 查询表元数据信息
     *
     * @param ds
     * @param tableName
     * @return
     */
    public List<Table> getTables(DataSource ds, String schema, String tableName) throws SQLException {
        Connection connection = null;
        try {
            //装载驱动类
            Class.forName(ds.getDriverClassName());
            //先获取jdbc连接
            connection = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());
            //从connection中获取数据库的元数据
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(connection.getCatalog(), "%" + schema + "%", "%" + tableName + "%", null);
            Database database = this.getDatabase(ds);
            log.info("tableName: " + tableName);
            List<Table> tables = new ArrayList<>();
            while (rs.next()) {
                Table table = Table.builder().database(database)
                        .cat(rs.getString("TABLE_CAT"))
                        .schema(rs.getString("TABLE_SCHEM"))
                        .name(rs.getString("TABLE_NAME"))
                        .type(rs.getString("TABLE_TYPE"))
                        .comment(rs.getString("REMARKS"))
                        .build();

                //主键
                ResultSet rsPrimaryKeys = databaseMetaData.getPrimaryKeys(connection.getCatalog(), rs.getString("TABLE_SCHEM"), rs.getString("TABLE_NAME"));
                List<String> primaryKeys = new ArrayList<>();
                while (rsPrimaryKeys.next()) {
                    primaryKeys.add(rsPrimaryKeys.getString("COLUMN_NAME"));
                }
                table.setPrimaryKey(primaryKeys);

                //查询表字段
                List<TableField> listTableFields = this.getTableFields(table, connection, databaseMetaData);
                log.debug("table {} TableField:{}", table.getName(), listTableFields);
                table.setFields(listTableFields);
                tables.add(table);
            }
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConn(connection);
        }
        return null;
    }

    public Database getDatabase(DataSource ds) {
        if (databaseCached.containsKey(ds.getUrl())) {
            return databaseCached.get(ds.getUrl());
        }
        Connection connection = null;
        try {
            //装载驱动类
            Class.forName(ds.getDriverClassName());
            //先获取jdbc连接
            connection = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());
            //从connection中获取数据库的元数据
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            Database database = Database.builder().name(connection.getCatalog())
                    .url(ds.getUrl())
                    .type(DatabaseTypeEnum.getEnumByCode(databaseMetaData.getDatabaseProductName()))
                    .productVersion(databaseMetaData.getDatabaseProductVersion())
                    .driverName(databaseMetaData.getDriverName())
                    .driverVersion(databaseMetaData.getDatabaseProductVersion())
                    .driverClassName(databaseMetaData.getDriverName())
                    .build();
            databaseCached.put(ds.getUrl(), database);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConn(connection);
        }
        return null;
    }

    public Long getTableCount(DataSource ds, Table table) {
        //数据库信息
        Database database = this.getDatabase(ds);
        Connection connection = null;
        try {
            //装载驱动类
            Class.forName(ds.getDriverClassName());
            //先获取jdbc连接
            connection = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());
            Statement stmt = connection.createStatement();
            String sql = GenerateSqlFactory
                    .getInstance(database.getType())
                    .count(table);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong("num");
            }
        } catch (Exception e) {
            log.error("getTableCount error {}", table);
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConn(connection);
        }
        return null;
    }

    public TableField getTableField(DataSource ds, String tableName, String fieldName) throws SQLException {
        Table table = this.getTable(ds, "", tableName);
        return table.getField(fieldName);
    }
}