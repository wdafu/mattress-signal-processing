package com.example.hospital.patient.wx.api.netty;

//import com.example.hospital.patient.wx.api.service.DummyDeviceDataRecordService;
import com.example.hospital.patient.wx.api.service.MattressDataService;

import com.example.hospital.patient.wx.api.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.Value;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgPackDecoder extends ByteToMessageDecoder {

   // @Resource(name = "mattressDataServiceImpl")


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf buf, List<Object> list) throws Exception {
        //这里得通过getbean获取不能通过注解方式
        MattressDataService mattressDataService = SpringUtil.getBean(MattressDataService.class);


        //数据校验
        int readableBytes = buf.readableBytes();
        System.out.println("bytes:" + readableBytes);

        // 获取可读字节数
        byte[] byteArray = new byte[111]; // 将 ByteBuf 中的内容读到 byteArray
        buf.getBytes(buf.readerIndex(), byteArray); // 逐个字节输出为十六进制

        System.out.println("ByteBuf in Hex: " + LocalDateTime.now());
        for (byte b : byteArray) {
            System.out.printf("%02x ", b); // 每个字节以两位十六进制形式输出
        }
        System.out.println();

        if (readableBytes != 111) {
            channelHandlerContext.close();
            return;
        }
        byte magic1 = buf.readByte();
        byte magic2 = buf.readByte();

        //数据校验
        boolean m1 = magic1 == (byte) 0xBA;
        boolean m2 = magic2 == (byte) 0xDC;
        if (!(m1 && m2)) {
            System.out.println("data wrong!");
            // 非法数据包，关闭连接
            channelHandlerContext.close();
            return;
        }
        //获取长度
        byte lenMsg = buf.readByte();
        byte crc = buf.readByte();

        //数据提取
        byte[] data = new byte[lenMsg];
        buf.readBytes(data);

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {

            System.out.println("test success1");
//            System.out.println(unpacker);
//            System.out.println(unpacker.unpackValue());
//            System.out.println(unpacker.unpackValue().asMapValue());

            Map<Value, Value> rawmap = unpacker.unpackValue().asMapValue().map();

            System.out.println("test success2");

            Map<String, Object> stringMap = new HashMap<>();


            for (Map.Entry<Value, Value> entry : rawmap.entrySet()) {
                String key = entry.getKey().asStringValue().asString();
                Value val = entry.getValue();

                if (val.getValueType().isArrayType()) {
                    ArrayValue arrayValue = val.asArrayValue();
                    System.out.println("arrayValue" + arrayValue);
                    List<Value> elements = arrayValue.list();
                    List<BigDecimal> integerList = new ArrayList<>();
                    for (Value element : elements) {
                        if (element.getValueType().isIntegerType()) {

                            BigDecimal bigDecimal = new BigDecimal(element.asIntegerValue().toInt());
                            BigDecimal fs = new BigDecimal("50");
                            BigDecimal var = bigDecimal.divide(fs);

                            integerList.add(var);
                        }
                        stringMap.put(key, integerList.toString());
                    }


                }  else if (val.getValueType().isIntegerType()) {
                    int dataValue = entry.getValue().asIntegerValue().asInt();
                    stringMap.put(key, dataValue);
                } else if (val.getValueType().isStringType()) {
                    String dataValue = entry.getValue().asStringValue().toString();
                    if (dataValue.equals("onn")){
                        dataValue = "on";
                    }
                    stringMap.put(key, dataValue);
                }
            }
            System.out.println(rawmap);
            System.out.println(stringMap);

            //数据存储
            mattressDataService.addMattressData(stringMap);

        }

    }

}