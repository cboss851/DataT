package com.space.datat.controller;

import com.space.datat.service.databaseinfo.DatabaseConstant;
import com.space.datat.service.databaseinfo.DatabaseInfoService;
import com.space.datat.service.databaseinfo.dto.TableDataListReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

@Api(tags = "查询")
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/database/query")
public class DatabaseFileController {
    private final DatabaseInfoService databaseInfoService;

    @ApiOperation("SQL-CSV")
    @GetMapping("/sqlCsv")
    public void tableJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = request.getParameter("sql");
        List<LinkedHashMap<String, Object>> result = databaseInfoService.tableDataList(
                TableDataListReq.builder()
                        .ds(DatabaseConstant.ds)
                        .sql(sql)
                        .build());
        if (result.size() == 0) {
            return;
        }
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("csv_" + System.currentTimeMillis(), "UTF-8") + ".csv");
        response.setContentType("text/csv");

        StringBuilder csvHead = new StringBuilder();
        result.get(0).forEach(
                (key, value) -> {
                    csvHead.append(key).append(",");
                });
        csvHead.setLength(csvHead.length() - 1); // Remove last comma
        csvHead.append("\n");
        output.write(csvHead.toString().getBytes());

        for (LinkedHashMap<String, Object> row : result) {
            StringBuilder csvRow = new StringBuilder();
            row.forEach(
                    (key, value) -> {
                        String valueStr = value == null ? "" : value.toString();
                        csvRow.append("\"").append(valueStr.replaceAll("\"", "\"\"")).append("\",");
                    });
            csvRow.setLength(csvRow.length() - 1); // Remove last comma
            csvRow.append("\n");
            output.write(csvRow.toString().getBytes());
        }

        output.close();
        return;
    }
}