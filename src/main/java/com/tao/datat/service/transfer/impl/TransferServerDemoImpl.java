package com.tao.datat.service.transfer.impl;

import com.tao.datat.service.database.DataSource;
import com.tao.datat.service.transfer.dto.TransferReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

@Slf4j
@Service
public class TransferServerDemoImpl {
    public void transfer(DataSource sourceDS, DataSource objectDS, List<TransferReq.TableReq> tables) {
        log.info("transfer start");
        log.info("sourceDS:{}", sourceDS);
        log.info("objectDS:{}", objectDS);
        log.info("tables:{}", tables);
        try {
            //装载驱动类
            Class.forName(sourceDS.getDriverClassName());
            //先获取jdbc连接
            Connection connection = DriverManager.getConnection(sourceDS.getUrl(), sourceDS.getUsername(), sourceDS.getPassword());
            //从connection中获取数据库的元数据
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("数据库已知的用户: " + databaseMetaData.getUserName());
            System.out.println("数据库的系统函数的逗号分隔列表: " + databaseMetaData.getSystemFunctions());
            System.out.println("数据库的时间和日期函数的逗号分隔列表: " + databaseMetaData.getTimeDateFunctions());
            System.out.println("数据库的字符串函数的逗号分隔列表: " + databaseMetaData.getStringFunctions());
            System.out.println("数据库供应商用于 'schema' 的首选术语: " + databaseMetaData.getSchemaTerm());
            System.out.println("数据库URL: " + databaseMetaData.getURL());
            System.out.println("是否允许只读:" + databaseMetaData.isReadOnly());
            System.out.println("数据库的产品名称:" + databaseMetaData.getDatabaseProductName());
            System.out.println("数据库的版本:" + databaseMetaData.getDatabaseProductVersion());
            System.out.println("驱动程序的名称:" + databaseMetaData.getDriverName());
            System.out.println("驱动程序的版本:" + databaseMetaData.getDriverVersion());

            for (TransferReq.TableReq table : tables) {
                //指定表元数据信息
                ResultSet rs = databaseMetaData.getColumns(null, null, table.getName(), null);
                while (rs.next()) {
                    String tableCat = rs.getString("TABLE_CAT");  //表类别（可能为空）
                    String tableSchemaName = rs.getString("TABLE_SCHEM");  //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
                    String tableName_ = rs.getString("TABLE_NAME");  //表名
                    String columnName = rs.getString("COLUMN_NAME");  //列名
                    int dataType = rs.getInt("DATA_TYPE");     //对应的java.sql.Types的SQL类型(列类型ID)
                    String dataTypeName = rs.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)
                    int columnSize = rs.getInt("COLUMN_SIZE");  //列大小
                    int decimalDigits = rs.getInt("DECIMAL_DIGITS");  //小数位数
                    int numPrecRadix = rs.getInt("NUM_PREC_RADIX");  //基数（通常是10或2） --未知
                    /**
                     *  0 (columnNoNulls) - 该列不允许为空
                     *  1 (columnNullable) - 该列允许为空
                     *  2 (columnNullableUnknown) - 不确定该列是否为空
                     */
                    int nullAble = rs.getInt("NULLABLE");  //是否允许为null
                    String remarks = rs.getString("REMARKS");  //列描述
                    String columnDef = rs.getString("COLUMN_DEF");  //默认值
                    int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");    // 对于 char 类型，该长度是列中的最大字节数
                    int ordinalPosition = rs.getInt("ORDINAL_POSITION");   //表中列的索引（从1开始）
                    /**
                     * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
                     * YES -- 该列可以有空值;
                     * NO -- 该列不能为空;
                     * 空字符串--- 不知道该列是否可为空
                     */
                    String isNullAble = rs.getString("IS_NULLABLE");
                    System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName_ + " - " + columnName + " - " + dataType + " - " + dataTypeName + " - " + columnSize + " - " + decimalDigits + " - " + numPrecRadix + " - " + nullAble + " - " + remarks + " - " + columnDef + " - " + charOctetLength + " - " + ordinalPosition + " - " + isNullAble);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("transfer end");
    }
}
