//package com.example.hospital.patient.wx.api.service;
//
//
//
//
//import cn.hutool.core.map.MapUtil;
//import com.example.hospital.patient.wx.api.config.TdengineConfigProperties;
//import com.example.hospital.patient.wx.api.db.dao.td.MetaMapper;
//import com.example.hospital.patient.wx.api.db.pojo.MattressDataEntity;
//import com.example.hospital.patient.wx.api.db.pojo.MetaDataEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Slf4j
//@Component
//public class DummyDeviceDataRecordService {
//
//    @Autowired
//    private TdengineConfigProperties tdengineconfig;
//
//    @Autowired
//    private MetaMapper tdengineMapper;
//
//    @Async("tdWriteExecutor")
//    public void saveRuntimePropertiesRecord(Map param) {
//        MetaDataEntity entry = new MetaDataEntity ();
//        int heartbeat = MapUtil.getInt(param, "hb");
//        int breath = MapUtil.getInt(param, "br");
//        int odor = MapUtil.getInt(param, "od");
//        int weight = MapUtil.getInt(param, "we");
//        String state = MapUtil.getStr(param, "st");
//        String position = MapUtil.getStr(param, "p");
//        String wet = MapUtil.getInt(param, "wt")==0?"N":"Y";
//        entry.setBreath(breath);
//        entry.setHeartbeat(heartbeat);
//        entry.setOdor(odor);
//        entry.setWeight(weight);
//        entry.setStatee(state);
//        entry.setPosition(position);
//        entry.setWet(wet);
//        entry.setTs(System.currentTimeMillis());
//        entry.setProduct_key("device1");
//        try {
//            tdengineMapper.insertDeviceMetaData(entry);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//}