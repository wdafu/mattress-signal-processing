package com.example.hospital.patient.wx.api.service;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.patient.wx.api.db.dao.MattressDao;
import com.example.hospital.patient.wx.api.db.pojo.MattressDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class MattressDataServiceImpl implements MattressDataService {

    @Autowired //是用在JavaBean中的注解，通过byType形式，用来给指定的字段或方法注入所需的外部资源。
    private MattressDao mattressDao; //jdbc连接工具类



    @Override
    @Transactional
    public void addMattressData(Map param) {
        MattressDataEntity entry = new MattressDataEntity();
        int heartbeat = MapUtil.getInt(param, "hb");
        int breath = MapUtil.getInt(param, "br");
        int osa = MapUtil.getInt(param, "OSA");
        int sleepStage = MapUtil.getInt(param, "sp");
        String state = MapUtil.getStr(param, "st");
        String HRV = MapUtil.getStr(param, "hrv");
        String deviceID = MapUtil.getStr(param, "ID");
        int key = MapUtil.getInt(param, "key");
        entry.setBreath(breath);
        entry.setHeartbeat(heartbeat);
        entry.setOSA(osa);
        entry.setSleepStage(sleepStage);
        entry.setState(state);
        entry.setHRV(HRV);
        entry.setDeviceID(deviceID);
        entry.setKey(key);
        try {
            mattressDao.insertFuData(entry);
        }catch (Exception e){
            e.printStackTrace();
        }

//        String sql = "insert into device1(heartbeat,breath,odor,weight,wet,state,position)values(?,?,?,?,?,?,?)";
//        Object[] params = {entity.getHeartbeat(),entity.getBreath(),entity.getOdor(),entity.getWeight(),entity.getWet(),entity.getState(), entity.getPosition()};
//        return jdbcTemplate.update(sql, params);
//        map.put("ma", (String) param.get("ma"));
//        map.put("mo", (String) param.get("mo"));
//        map.put("sn", (String) param.get("sn"));
//        map.put("v", (String) param.get("v"));
                System.out.println(param);
    }

}
