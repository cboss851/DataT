package com.tao.datat.service.database.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DatabaseTypeEnum {
    MySQL("MySQL", "MySQL"),
    SqlServer("Microsoft SQL Server", "Microsoft SQL Server"),
    Oracle("Oracle", "Oracle");

    private String code;

    private String message;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DatabaseTypeEnum getEnumByCode(String code) {
        if (code == null)
            return null;
        for (DatabaseTypeEnum _enum : values()) {
            if (_enum.getCode().equalsIgnoreCase(code)) {
                return _enum;
            }
        }
        return null;
    }
}