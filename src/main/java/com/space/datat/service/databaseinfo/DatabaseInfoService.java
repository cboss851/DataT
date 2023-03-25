package com.space.datat.service.databaseinfo;

import com.space.datat.service.database.DataSource;
import com.space.datat.service.database.Database;
import com.space.datat.service.database.Table;
import com.space.datat.service.databaseinfo.dto.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface DatabaseInfoService {
    Database queryDatabaseInfo(DataSource ds) throws SQLException;

    List<Table> queryTableInfo(TableQueryReq req) throws SQLException;

    List<TableNameListRsp> queryTableBaseInfo(TableNameListReq req) throws SQLException;

    List<TableCountListRsp> queryTableCount(TableCountListReq req) throws SQLException;

    List<LinkedHashMap<String,Object>> tableDataList(TableDataListReq req);
}
