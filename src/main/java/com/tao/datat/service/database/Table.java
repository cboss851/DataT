package com.tao.datat.service.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table  {
    private Database database;
    private List<TableField> fields;
    private String cat;
    private String schema;
    private String name;
    private String type;
    private String comment;
    private Long count;
    private List<String> primaryKey;

    public TableField getField(String fieldName){
        for (TableField field : fields) {
            if(fieldName.equalsIgnoreCase(field.getName())){
                return field;
            }
        }
        return null;
    }
}
