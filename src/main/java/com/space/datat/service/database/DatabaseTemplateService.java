package com.space.datat.service.database;

import com.space.datat.service.database.sql.GenerateSqlFactory;
import com.space.datat.service.database.utils.JdbcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseTemplateService {

    /**
     * sql语句查询
     *
     * @param ds
     * @param sql
     * @return
     */
    public List<LinkedHashMap<String, Object>> queryForList(DataSource ds, String sql) {
        log.info("queryForList sql:{}", sql);
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<LinkedHashMap<String, Object>>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = meta.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnStmtRest(rs, stmt, conn);
        }
        return resultList;
    }
}
