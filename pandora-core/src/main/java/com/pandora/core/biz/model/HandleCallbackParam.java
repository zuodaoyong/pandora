package com.pandora.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleCallbackParam implements Serializable {
    private static final long serialVersionUID = -4757960696094746606L;

    private long logId;

    private long logDateTim;

    private int handleCode;

    private String handleMsg;

}
