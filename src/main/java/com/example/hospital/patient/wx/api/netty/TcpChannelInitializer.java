package com.example.hospital.patient.wx.api.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;


@Slf4j
@Component
public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {


    //MessageUnpacker packer=new MessagePack();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline=socketChannel.pipeline();
        pipeline.addLast(new MsgPackDecoder());
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast( new MsgPackEncoder());
        pipeline.addLast(new MsgMessageHandler());


    }
}
