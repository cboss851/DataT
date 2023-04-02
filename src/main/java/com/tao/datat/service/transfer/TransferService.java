package com.tao.datat.service.transfer;

import com.tao.datat.service.transfer.dto.TransferReq;
import com.tao.datat.service.database.DataSource;

import java.sql.SQLException;
import java.util.List;

public interface TransferService {

    void transfer(DataSource sourceDS, DataSource objectDS, List<TransferReq.TableReq> tables) throws SQLException;
}
