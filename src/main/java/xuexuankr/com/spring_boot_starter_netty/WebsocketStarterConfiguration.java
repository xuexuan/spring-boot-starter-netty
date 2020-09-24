package xuexuankr.com.spring_boot_starter_netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({WebsocketServer.class})
@EnableConfigurationProperties(WebsocketStarterProperties.class)
public class WebsocketStarterConfiguration {

	@Autowired
	private WebsocketStarterProperties properties;
	
	@Bean
	@ConditionalOnMissingBean(WebsocketServer.class)
	public WebsocketServer getWebsocketServer() {
		WebsocketServer server = new WebsocketServer();
		server.setPort(properties.getPort());
		return server;
	}
	
	@Bean
	@ConditionalOnMissingBean(AnnotationObjects.class)
	public AnnotationObjects getAnnotationObjects() {
		AnnotationObjects objects = new AnnotationObjects();
		return objects;
	}
}
