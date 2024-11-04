package com.example.hospital.patient.wx.api.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class MsgMessageHandler extends ChannelHandlerAdapter {
    private int counter=0;

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }


    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        List<Object> students = (List<Object>) msg;
        for (Object student : students) {
            System.out.println("属性：" + student);
        }
        System.out.println("counter:"+ ++counter);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}