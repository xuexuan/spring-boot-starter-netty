package xuexuankr.com.spring_boot_starter_netty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WsChannelInitializer  extends ChannelInitializer {
	Logger _log = LoggerFactory.getLogger(WsChannelInitializer.class);
	
	AnnotationObjects objects;
	
	public WsChannelInitializer(AnnotationObjects obj_) {
		objects = obj_;
	}
	
	
	@Override
    protected void initChannel(Channel ch) {
		PojoEndpointServer server = null;
		try {
			if (objects == null)
			{
				_log.error("object autowired equal to null");
			}
			Object impl= objects.GetImplement(ServerEndpoint.class);
			
			if (impl instanceof PojoEndpointServer)
			{
				server = (PojoEndpointServer)impl;
			}
			else
			{
				server = new PojoEndpointServer();
			}
		}
		catch(Exception e)
		{
			_log.error("Fail to create server instance " + e);
			server = new PojoEndpointServer();
		}
		
        ChannelPipeline pipeline = ch.pipeline();
        // websocket是基于http协议的，所以需要使用http编解码器
        pipeline.addLast(new HttpServerCodec())
                // 对写大数据流的支持
                .addLast(new ChunkedWriteHandler())
                // 对http消息的聚合，聚合成FullHttpRequest或FullHttpResponse
                // 在Netty的编程中，几乎都会使用到这个handler
                .addLast(new HttpObjectAggregator(1024 * 64));
        // 以上三个处理器是对http协议的支持

        // websocket 服务器处理的协议，并用于指定客户端连接的路由(这里指定的是 /ws)
        // 这里的URL就是 ws://ip:port/ws
        // 该处理器为运行websocket服务器承担了所有繁重的工作
        // 它会负责websocket的握手以及处理控制帧
        // websocket的数据传输都是以frames进行的
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义的处理器
        pipeline.addLast(new WsServerHandler(server));
    }
}

