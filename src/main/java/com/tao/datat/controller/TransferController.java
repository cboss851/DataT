package com.tao.datat.controller;

import com.tao.commons.result.ResponseResult;
import com.tao.datat.service.transfer.TransferService;
import com.tao.datat.service.transfer.dto.TransferReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Api(tags = "数据传输")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @ApiOperation("传输DS")
    @PostMapping("/transferDS")
    public ResponseResult transferDS(@Validated @RequestBody TransferReq req) throws SQLException {
        transferService.transfer(req.getSourceDS(), req.getObjectDS(), req.getTables());
        return ResponseResult.success();
    }
}
