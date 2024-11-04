package com.example.hospital.patient.wx.api.db.dao;


import com.example.hospital.patient.wx.api.db.pojo.MattressDataEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface MattressDao {
    public int insertFuData(MattressDataEntity entity);

}




