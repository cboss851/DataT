package com.space.datat.service.transfer;

import com.space.datat.service.transfer.dto.TransferReq;
import com.space.datat.service.database.DataSource;

import java.sql.SQLException;
import java.util.List;

public interface TransferService {

    void transfer(DataSource sourceDS, DataSource objectDS, List<TransferReq.TableReq> tables) throws SQLException;
}
