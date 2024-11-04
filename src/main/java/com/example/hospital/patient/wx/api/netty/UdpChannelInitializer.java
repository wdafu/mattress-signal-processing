package com.example.hospital.patient.wx.api.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UdpChannelInitializer extends ChannelInitializer<NioDatagramChannel> {


    //MessageUnpacker packer=new MessagePack();

    @Override
    protected void initChannel(NioDatagramChannel socketChannel) throws Exception {
        ChannelPipeline pipeline=socketChannel.pipeline();
        //pipeline.addLast( new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
        pipeline.addLast(new ThreeUdpServerHandler());
//        pipeline.addLast(new MsgPackDecoder());




    }
}
