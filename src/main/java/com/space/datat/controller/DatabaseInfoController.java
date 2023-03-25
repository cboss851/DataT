package com.space.datat.controller;

import com.space.datat.common.utils.Result;
import com.space.datat.service.database.DataSource;
import com.space.datat.service.database.Database;
import com.space.datat.service.database.Table;
import com.space.datat.service.databaseinfo.DatabaseConstant;
import com.space.datat.service.databaseinfo.DatabaseInfoService;
import com.space.datat.service.databaseinfo.dto.*;
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
    public Result<Database> info(@Validated @RequestBody DatabaseInfoReq req) throws SQLException {
        DatabaseConstant.ds = req.getDs();
        return Result.success(databaseInfoService.queryDatabaseInfo(req.getDs()));
    }

    @ApiOperation("表及字段信息")
    @PostMapping("/table")
    public Result<List<Table>> table(@Validated @RequestBody TableQueryReq req) throws SQLException {
        return Result.success(databaseInfoService.queryTableInfo(req));
    }

    @ApiOperation("表列表")
    @PostMapping("/tableNameList")
    public Result<List<TableNameListRsp>> tableNameList(@Validated @RequestBody TableNameListReq req) throws SQLException {
        return Result.success(databaseInfoService.queryTableBaseInfo(req));
    }

    @ApiOperation("表列表-数量")
    @PostMapping("/tableCountList")
    public Result<List<TableCountListRsp>> tableCountList(@Validated @RequestBody TableCountListReq req) throws SQLException {
        return Result.success(databaseInfoService.queryTableCount(req));
    }

    @ApiOperation("SQL-JSON")
    @PostMapping("/sqlJSON")
    public Result<List<LinkedHashMap<String, Object>>> tableDataList(@Validated @RequestBody TableDataListReq req) throws SQLException {
        return Result.success(databaseInfoService.tableDataList(req));
    }

    @ApiOperation("连接例子")
    @GetMapping("/dataSourceList")
    public Result<List<DataSource>> dataSourceList() {
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
        return Result.success(dataSourceList);
    }

}