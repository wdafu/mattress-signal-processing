package com.example.hospital.patient.wx.api.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.msgpack.core.MessagePack;
import org.springframework.stereotype.Component;

@Component
public class MsgPackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//        MessagePack msgPack = new MessagePack();
//        byte[] raw = msgPack.write(msg);
//        out.writeBytes(raw);
//        System.out.println("en:  "+out);
    }
}
