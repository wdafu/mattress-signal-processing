package com.example.hospital.patient.wx.api.db.pojo;

import lombok.Data;

@Data

public class MattressDataEntity {

    private Integer heartbeat;
    private Integer breath;
    private Integer OSA;
    private Integer sleepStage;
    private String state;
    private String HRV;
    private String deviceID;
    private Integer key;

}