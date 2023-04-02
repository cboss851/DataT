package com.tao.datat.service.transfer;

import com.tao.datat.service.database.DataSource;
import com.tao.datat.service.database.Database;
import com.tao.datat.service.database.TableData;

import java.sql.SQLException;

public interface TransferManagerService {
    //保存表数据
    void saveTableData(TableData tableData, DataSource objectDS, Database databaseObject) throws SQLException;
}
