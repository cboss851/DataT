package com.tao.datat.service.databaseinfo;

import com.tao.datat.service.database.DataSource;
import com.tao.datat.service.database.Database;
import com.tao.datat.service.database.Table;
import com.tao.datat.service.databaseinfo.dto.*;

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
