package com.tao.datat.service.databaseinfo.impl;

import com.tao.commons.utils.BeanUtils;
import com.tao.datat.service.database.*;
import com.tao.datat.service.databaseinfo.DatabaseInfoService;
import com.tao.datat.service.databaseinfo.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseInfoServiceImpl implements DatabaseInfoService {
    private final DatabaseMetaService databaseMetaService;
    private final DatabaseTemplateService databaseTemplateService;

    @Override
    public Database queryDatabaseInfo(DataSource ds) throws SQLException {
        //数据库信息
        Database database = databaseMetaService.getDatabase(ds);

        return database;
    }

    @Override
    public List<Table> queryTableInfo(TableQueryReq req) throws SQLException {
        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), req.getSchema(), req.getName());

        return tables;
    }

    @Override
    public List<TableNameListRsp> queryTableBaseInfo(TableNameListReq req) throws SQLException {
        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), req.getSchema(), req.getName());

        return BeanUtils.copyBeanList(tables, TableNameListRsp.class);
    }

    @Override
    public List<TableCountListRsp> queryTableCount(TableCountListReq req) throws SQLException {
        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), req.getSchema(), req.getName());
        //查询数量
        for (Table table : tables) {
            table.setCount(databaseMetaService.getTableCount(req.getDs(), table));
        }
        return BeanUtils.copyBeanList(tables, TableCountListRsp.class);
    }

    @Override
    public List<LinkedHashMap<String, Object>> tableDataList(TableDataListReq req) {
        return databaseTemplateService.queryForList(req.getDs(), req.getSql());
    }

    @Override
    public List<TableField> sqlMetaData(MetaDataListReq req) {
        return databaseTemplateService.sqlMetaData(req.getDs(), req.getSql());
    }
}
