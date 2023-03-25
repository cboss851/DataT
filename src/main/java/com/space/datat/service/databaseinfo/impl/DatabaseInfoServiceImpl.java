package com.space.datat.service.databaseinfo.impl;

import com.space.datat.service.database.*;
import com.space.datat.service.databaseinfo.DatabaseInfoService;
import com.space.datat.service.databaseinfo.dto.*;
import com.space.datat.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
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
        //数据库信息
        Database database = databaseMetaService.getDatabase(req.getDs());

        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), database, req.getSchema(), req.getName());

        return tables;
    }

    @Override
    public List<TableNameListRsp> queryTableBaseInfo(TableNameListReq req) throws SQLException {
        //数据库信息
        Database database = databaseMetaService.getDatabase(req.getDs());

        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), database, req.getSchema(), req.getName());

        return BeanUtils.copyBeanFromNewList(tables, TableNameListRsp.class);
    }

    @Override
    public List<TableCountListRsp> queryTableCount(TableCountListReq req) throws SQLException {
        //数据库信息
        Database database = databaseMetaService.getDatabase(req.getDs());

        //查询表元数据信息
        List<Table> tables = databaseMetaService.getTables(req.getDs(), database, req.getSchema(), req.getName());

        //查询数量
        for (Table table : tables) {
            table.setCount(databaseMetaService.getTableCount(req.getDs(), database, table));
        }
        return BeanUtils.copyBeanFromNewList(tables, TableCountListRsp.class);
    }

    @Override
    public List<LinkedHashMap<String, Object>> tableDataList(TableDataListReq req) {
        return databaseTemplateService.queryForList(req.getDs(), req.getSql());
    }
}
