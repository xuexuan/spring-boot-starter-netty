package xuexuankr.com.spring_boot_starter_netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WsServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	Logger _log = LoggerFactory.getLogger(WsServerHandler.class);
   
    private PojoEndpointServer server;
    public WsServerHandler(PojoEndpointServer server_) {
    	server = server_;
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    	server.onMessage(ctx.channel(), msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	server.onError(ctx.channel(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	server.onClose(ctx.channel());
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        server.onOpen(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    	server.onEvent(ctx.channel(), evt);
    }

}