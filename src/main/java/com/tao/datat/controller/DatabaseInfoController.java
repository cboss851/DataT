package com.tao.datat.controller;

import com.tao.commons.result.ResponseResult;
import com.tao.datat.service.database.DataSource;
import com.tao.datat.service.database.Database;
import com.tao.datat.service.database.Table;
import com.tao.datat.service.database.TableField;
import com.tao.datat.service.databaseinfo.DatabaseConstant;
import com.tao.datat.service.databaseinfo.DatabaseInfoService;
import com.tao.datat.service.databaseinfo.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Api(tags = "数据库信息")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/database")
public class DatabaseInfoController {
    private final DatabaseInfoService databaseInfoService;

    @ApiOperation("数据库信息")
    @PostMapping("/databaseInfo")
    public ResponseResult<Database> info(@Validated @RequestBody DatabaseInfoReq req) throws SQLException {
        DatabaseConstant.ds = req.getDs();
        return ResponseResult.successData(databaseInfoService.queryDatabaseInfo(req.getDs()));
    }

    @ApiOperation("表及字段信息")
    @PostMapping("/table")
    public ResponseResult<List<Table>> table(@Validated @RequestBody TableQueryReq req) throws SQLException {
        return ResponseResult.successData(databaseInfoService.queryTableInfo(req));
    }

    @ApiOperation("表列表")
    @PostMapping("/tableNameList")
    public ResponseResult<List<TableNameListRsp>> tableNameList(@Validated @RequestBody TableNameListReq req) throws SQLException {
        return ResponseResult.successData(databaseInfoService.queryTableBaseInfo(req));
    }

    @ApiOperation("表列表-数量")
    @PostMapping("/tableCountList")
    public ResponseResult<List<TableCountListRsp>> tableCountList(@Validated @RequestBody TableCountListReq req) throws SQLException {
        return ResponseResult.successData(databaseInfoService.queryTableCount(req));
    }

    @ApiOperation("SQL-MetaData")
    @PostMapping("/sqlMetaData")
    public ResponseResult<List<TableField>> sqlMetaData(@Validated @RequestBody MetaDataListReq req) throws SQLException {
        return ResponseResult.successData(databaseInfoService.sqlMetaData(req));
    }

    @ApiOperation("SQL-JSON")
    @PostMapping("/sqlJSON")
    public ResponseResult<List<LinkedHashMap<String, Object>>> tableDataList(@Validated @RequestBody TableDataListReq req) throws SQLException {
        return ResponseResult.successData(databaseInfoService.tableDataList(req));
    }

    @ApiOperation("连接例子")
    @GetMapping("/dataSourceList")
    public ResponseResult<List<DataSource>> dataSourceList() {
        List<DataSource> dataSourceList = new ArrayList<>();
        dataSourceList.add(DataSource.builder()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/datat?useUnicode=true&allowMultiQueries=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useSSL=false&serverTimezone=Asia/Shanghai&&allowPublicKeyRetrieval=true")
                .username("root")
                .password("ea8dc0d1fe7e4073ad0296ea2eacc651")
                .build());
        dataSourceList.add(DataSource.builder()
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .url("jdbc:sqlserver://localhost:1433;database=source")
                .username("sa")
                .password("ea8dc0d1fe7e4073ad0296ea2eacc666")
                .build());
        return ResponseResult.successData(dataSourceList);
    }

}