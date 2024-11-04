package com.example.hospital.patient.wx.api;





import com.example.hospital.patient.wx.api.netty.TcpChannelInitializer;
import com.example.hospital.patient.wx.api.netty.UdpChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;


@EnableAsync
@ServletComponentScan
@ComponentScan("com.example.*")
@MapperScan("com.example.hospital.patient.wx.api.db.dao")
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PatientWxApiApplication {

    private  static final Logger log= LoggerFactory.getLogger(PatientWxApiApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(PatientWxApiApplication.class, args);
    }

    @Component
    static class AfterStart implements ApplicationRunner{

        //@Value("${udp.server.porzt}")
        @Value("63333")
        private int udpPort;

        //@Value("${tcp.server.port}")
        @Value("63333")
        private int tcpPort;
        //具体处理方法
        @Autowired
        private TcpChannelInitializer tcpChannelInitializer;

        @Autowired
        private UdpChannelInitializer udpChannelInitializer;


        //tcp连接
        @Override
        public void run(ApplicationArguments args) throws Exception {

            log.info("starting TCP server , port:{}",tcpPort);
            new Thread(()->{
                EventLoopGroup bossGroup=new NioEventLoopGroup();
                EventLoopGroup workerGroup=new NioEventLoopGroup();
                try{
                    ServerBootstrap serverBootstrap=new ServerBootstrap();
                    serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                            .option(ChannelOption.SO_BACKLOG,1024*128)
                            .option(ChannelOption.SO_RCVBUF,1024*128)
                            .option(ChannelOption.RCVBUF_ALLOCATOR,new FixedRecvByteBufAllocator(128*1024))
                            .childHandler(tcpChannelInitializer).childOption(ChannelOption.SO_RCVBUF,1024*128).childOption(ChannelOption.TCP_NODELAY,false);
                    ChannelFuture future =serverBootstrap.bind(tcpPort).sync();
                    future.channel().closeFuture().sync();

                }catch (InterruptedException e){
                    log.error("Start TCP Server failed. ", e);
                }finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }).start();
//            log.info("starting UDP server , port:{}",udpPort);
//            new Thread(()->{
//                EventLoopGroup group=new NioEventLoopGroup();
//                try{
//                    Bootstrap bootstrap = new Bootstrap();
//                    bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_REUSEADDR,true)
//                            .handler(udpChannelInitializer);
//                    ChannelFuture future =bootstrap.bind(udpPort).sync();
//                    future.channel().closeFuture().sync();
//
//                }catch (InterruptedException e){
//                    log.error("Start UDP Server failed. ", e);
//                }finally {
//                    group.shutdownGracefully();
//                }
//            }).start();

        }
    }

}
