package com.space.datat.service.transfer;

import com.space.datat.service.database.DataSource;
import com.space.datat.service.database.Database;
import com.space.datat.service.database.TableData;

import java.sql.SQLException;

public interface TransferManagerService {
    //保存表数据
    void saveTableData(TableData tableData, DataSource objectDS, Database databaseObject) throws SQLException;
}
