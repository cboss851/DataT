package com.space.datat.service.database;

import com.space.datat.service.database.enums.DatabaseTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Database {
    private String name;
    private String url;
    private DatabaseTypeEnum type;
    private String productVersion;
    private String driverName;
    private String driverVersion;
    private String driverClassName;
}