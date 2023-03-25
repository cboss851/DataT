package com.space.datat.service.transfer.impl;

import com.space.datat.service.database.DataSource;
import com.space.datat.service.database.Database;
import com.space.datat.service.database.TableData;
import com.space.datat.service.database.TableDataColumn;
import com.space.datat.service.database.enums.DatabaseTypeEnum;
import com.space.datat.service.database.sql.GenerateSqlFactory;
import com.space.datat.service.transfer.TransferManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferManagerServiceImpl implements TransferManagerService {
    //批量保存数量
    private final static int batchSize = 666;

    /**
     * 保存表数据
     *  @param tableData
     * @param objectDS
     * @param databaseObject
     */
    @Override
    public void saveTableData(TableData tableData, DataSource objectDS, Database databaseObject) throws SQLException {
        Long start = System.currentTimeMillis();

        //按batchSize分组
        List<List<List<TableDataColumn>>> batches = new ArrayList<>();
        for (int i = 0; i < tableData.getRows().size(); i += batchSize) {
            int end = Math.min(i + batchSize, tableData.getRows().size());
            List<List<TableDataColumn>> batch = tableData.getRows().subList(i, end);
            batches.add(batch);
        }

        //分组保存
        for (List<List<TableDataColumn>> batch : batches) {
            this.saveTableDataBatch(tableData, batch, objectDS,databaseObject);
        }
        log.info("insert table 表名：{},数量：{},时间：{}s", tableData.getTable().getName(), tableData.getRows().size(), (System.currentTimeMillis() - start) / 1000);
    }

    /**
     * 保存一组批量数据
     *  @param tableData
     * @param batch
     * @param objectDS
     * @param databaseObject
     */
    private void saveTableDataBatch(TableData tableData, List<List<TableDataColumn>> batch, DataSource objectDS, Database databaseObject) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String url = objectDS.getUrl();
        String username = objectDS.getUsername();
        String password = objectDS.getPassword();
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 开始批处理
            if (DatabaseTypeEnum.SqlServer.equals(databaseObject.getType())) {
                conn.createStatement().executeUpdate("SET IDENTITY_INSERT " + tableData.getTable().getName() + " ON");
            }
            String insertSql = GenerateSqlFactory
                    .getInstance(tableData.getTable().getDatabase().getType())
                    .insert(tableData.getTable());
            pstmt = conn.prepareStatement(insertSql);
            for (int i = 0; i < batch.size(); i++) {
                List<TableDataColumn> row = batch.get(i);
                for (int j = 0; j < row.size(); j++) {
                    TableDataColumn column = row.get(j);
                    int sqlType = column.getField().getDataType();
                    switch (sqlType) {
                        case Types.CHAR:
                            pstmt.setString(j + 1, (java.lang.String) column.getValue());
                            break;

                        case Types.VARCHAR:
                            pstmt.setString(j + 1, (java.lang.String) column.getValue());
                            break;

                        case Types.LONGVARCHAR:
                            pstmt.setString(j + 1, (java.lang.String) column.getValue());
                            break;

                        case Types.NUMERIC:
                            pstmt.setBigDecimal(j + 1, (java.math.BigDecimal) column.getValue());
                            break;

                        case Types.DECIMAL:
                            pstmt.setBigDecimal(j + 1, (java.math.BigDecimal) column.getValue());
                            break;

                        case Types.BIT:
                            pstmt.setBoolean(j + 1, (java.lang.Boolean) column.getValue());
                            break;

                        case Types.TINYINT:
                            pstmt.setByte(j + 1, (java.lang.Byte) column.getValue());
                            break;

                        case Types.SMALLINT:
                            pstmt.setShort(j + 1, (java.lang.Short) column.getValue());
                            break;

                        case Types.INTEGER:
                            pstmt.setInt(j + 1, (java.lang.Integer) column.getValue());
                            break;

                        case Types.BIGINT:
                            pstmt.setLong(j + 1, (java.lang.Long) column.getValue());
                            break;

                        case Types.REAL:
                            pstmt.setFloat(j + 1, (java.lang.Float) column.getValue());
                            break;

                        case Types.FLOAT:
                            pstmt.setFloat(j + 1, (java.lang.Float) column.getValue());
                            break;
                        case Types.DOUBLE:
                            pstmt.setDouble(j + 1, (java.lang.Double) column.getValue());
                            break;

                        case Types.DATE:
                            pstmt.setDate(j + 1, (java.sql.Date) column.getValue());
                            break;

                        case Types.TIME:
                            pstmt.setTime(j + 1, (java.sql.Time) column.getValue());
                            break;

                        case Types.TIMESTAMP:
                            pstmt.setTimestamp(j + 1, (java.sql.Timestamp) column.getValue());
                            break;

                        case Types.BLOB:
                            pstmt.setBlob(j + 1, (java.sql.Blob) column.getValue());
                            break;

                        case Types.CLOB:
                            pstmt.setClob(j + 1, (java.sql.Clob) column.getValue());
                            break;

                        default:
                            pstmt.setString(j + 1, (java.lang.String) column.getValue());
                    }
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
            if (DatabaseTypeEnum.SqlServer.equals(databaseObject.getType())) {
                conn.createStatement().executeUpdate("SET IDENTITY_INSERT " + tableData.getTable().getName() + " OFF");
            }
            log.info("table name {} insert,batch:{}", tableData.getTable().getName(), batch.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}