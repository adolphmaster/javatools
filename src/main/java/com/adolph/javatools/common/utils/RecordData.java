package com.adolph.javatools.common.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordData {
    private String acount;
    private String decisionEndDate;
    private String decisionEndTime;
    private String decisionType;
    private String decisionResult;
    private String adjustResult;
}
