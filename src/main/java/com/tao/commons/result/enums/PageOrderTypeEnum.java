package com.tao.commons.result.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分页排序类型
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PageOrderTypeEnum {
    ASC("asc", "升序"),
    DESC("desc", "降序"),;

    private String code;

    private String message;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PageOrderTypeEnum getEnumByCode(String code) {
        if (code == null)
            return null;
        for (PageOrderTypeEnum _enum : values()) {
            if (_enum.getCode().equalsIgnoreCase(code)) {
                return _enum;
            }
        }
        return null;
    }
}