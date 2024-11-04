package com.example.hospital.patient.wx.api.netty;

import com.example.hospital.patient.wx.api.service.MattressDataService;
import com.example.hospital.patient.wx.api.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.Value;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	 <B>说	明<B/>:
 *
 */
@Slf4j
public class ThreeUdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //当channel就绪后。
//        Channel incoming = ctx.channel();
//        System.out.println("UDP-Client:" + incoming.remoteAddress() + "上线");
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
            throws Exception {
        // 接受client的消息
        log.info("开始接收来自client的数据");
        MattressDataService mattressDataService = SpringUtil.getBean(MattressDataService.class);


        ByteBuf buf = Unpooled.buffer(1024);
        buf = msg.content();
        int readableBytes = buf.readableBytes();
        if(readableBytes!=76) {
            return;
        }
        System.out.println(readableBytes);

//        byte magic1 = buf.readByte();
//        byte magic2 = buf.readByte();
//        byte magic3 = buf.readByte();
//        byte magic4 = buf.readByte();
//        boolean m1 = magic1 == (byte) 0xAB;
//        boolean m2 = magic3 == (byte) 0xCD;
//        if (!(m1 && m2)) {
//            System.out.println("wrong:" + magic1 + ",wrong2:" + magic3);
//            System.out.println("raw wrong!");
//            // 非法数据包，关闭连接
//
//            return;
//        }
//
//
//        String len1 = Integer.toHexString(0xFF & (buf.readByte()));
//        String len2 = Integer.toHexString(0xFF & (buf.readByte()));
//        int len = Integer.parseInt(len2, 16) * 16 * 16 + Integer.parseInt(len1, 16);
//        int raw;
//
//        byte[] rawData = new byte[len];
//
//        buf.readBytes(rawData);
//
//        List<Integer> rawList = new ArrayList<>();
//
//        for (int i = 0; i < len; i += 2) {
//            len1 = Integer.toHexString(0xFF & (rawData[i]));
//
//            len2 = Integer.toHexString(0xFF & (rawData[i + 1]));
//
//            raw = Integer.parseInt(len2, 16) * 16 * 16 + Integer.parseInt(len1, 16);
//            rawList.add(raw);
//        }
//        mattressDataService.addRawData(rawList);
//        System.out.println(rawList);

        byte magic5 = buf.readByte();
        byte magic6 = buf.readByte();

        boolean m5 = magic5 == (byte) 0xBA;
        boolean m6 = magic6 == (byte) 0xDC;
        if (!(m5 && m6)) {
            System.out.println("data wrong!");
            // 非法数据包，关闭连接
            ctx.close();
            return;
        }

        byte lenMsg = buf.readByte();
        byte crc = buf.readByte();

        byte[] data = new byte[lenMsg];
        buf.readBytes(data);

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {
            Map<Value, Value> rawmap = unpacker.unpackValue().asMapValue().map();

            Map<String, Object> stringMap = new HashMap<>();

            for (Map.Entry<Value, Value> entry : rawmap.entrySet()) {
                String key = entry.getKey().asStringValue().asString();
                Value val = entry.getValue();
                if (val.isMapValue()) {
                    Map<Value, Value> dataMap = entry.getValue().asMapValue().map();

                    for (Map.Entry<Value, Value> dataEntry : dataMap.entrySet()) {
                        String dataKey = dataEntry.getKey().asStringValue().asString();

                        Value value = dataEntry.getValue();

                        if (value.getValueType().isArrayType()) {
                            ArrayValue arrayValue = value.asArrayValue();
                            List<Value> elements = arrayValue.list();

                            List<Integer> integerList = new ArrayList<>();
                            for (Value element : elements) {
                                if (element.getValueType().isIntegerType()) {
                                    integerList.add(element.asIntegerValue().toInt());
                                }
                                stringMap.put(dataKey, integerList.toString());
                            }
                        } else if (value.getValueType().isIntegerType()) {
                            int dataValue = dataEntry.getValue().asIntegerValue().asInt();
                            stringMap.put(dataKey, dataValue);
                        } else if (value.getValueType().isBooleanType()) {
                            int dataValue = dataEntry.getValue().asBooleanValue().getBoolean() ? 1 : 0;
                            stringMap.put(dataKey, dataValue);
                        } else if (value.getValueType().isStringType()) {
                            String dataValue = dataEntry.getValue().asStringValue().toString();
                            stringMap.put(dataKey, dataValue);
                        }

                    }


                } else if (val.getValueType().isIntegerType()) {
                    int dataValue = entry.getValue().asIntegerValue().asInt();
                    stringMap.put(key, dataValue);
                } else if (val.getValueType().isStringType()) {
                    String dataValue = entry.getValue().asStringValue().toString();
                    stringMap.put(key, dataValue);
                }
            }
            System.out.println(rawmap);

            mattressDataService.addMattressData(stringMap);
//            DummyDeviceDataRecordService mattressDataService = SpringUtil.getBean(DummyDeviceDataRecordService.class);
//            mattressDataService.saveRuntimePropertiesRecord(stringMap);
            // 将解析后的Map对象添加到输出列表中，供后续处理器处理

        }
    }

        //捕获异常
        @Override
        public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error("UdpServerHandler exceptionCaught" + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }

    }
