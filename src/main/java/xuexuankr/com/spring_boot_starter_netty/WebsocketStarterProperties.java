package xuexuankr.com.spring_boot_starter_netty;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "websocketstarter")
public class WebsocketStarterProperties {
	private int _port = 8088;
	
	public int getPort() {
		return _port;
	}
	
	public void setPort(int port_) {
		_port = port_;
	}
}