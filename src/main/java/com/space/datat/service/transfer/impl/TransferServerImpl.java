package com.space.datat.service.transfer.impl;

import com.space.datat.service.database.DataSource;
import com.space.datat.service.database.Database;
import com.space.datat.service.database.Table;
import com.space.datat.service.database.TableData;
import com.space.datat.service.database.DatabaseMetaService;
import com.space.datat.service.transfer.TransferManagerService;
import com.space.datat.service.transfer.TransferService;
import com.space.datat.service.transfer.dto.TransferReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServerImpl implements TransferService {
    private final TransferManagerService transferManagerService;
    private final DatabaseMetaService databaseMetaService;

    @Override
    public void transfer(DataSource sourceDS, DataSource objectDS, List<TransferReq.TableReq> tables) throws SQLException {
        log.info("transfer start");
        log.info("sourceDS:{}", sourceDS);
        log.info("objectDS:{}", objectDS);
        log.info("tables:{}", tables);
        //数据库信息
        Database databaseSource = databaseMetaService.getDatabase(sourceDS);
        Database databaseObject = databaseMetaService.getDatabase(objectDS);
        log.info("database source:{}", databaseSource);
        //循环处理所有表
        for (TransferReq.TableReq tableReq : tables) {
            try {
                //查询表元数据信息
                Table table = databaseMetaService.getTable(sourceDS, databaseSource, "", tableReq.getName());
                log.info("table:{}", table);

                //查询表数据
                TableData tableData = databaseMetaService.getTableData(table, sourceDS, tableReq.getCount());
                log.info("TableData 表名：{},数量：{}", table.getName(), tableData.getRows().size());

                //保存表数据
                transferManagerService.saveTableData(tableData, objectDS,databaseObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("transfer end");
    }
}