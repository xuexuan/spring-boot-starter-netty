package xuexuankr.com.spring_boot_starter_netty;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public class AnnotationObjects {

	Logger _log = LoggerFactory.getLogger(WebsocketServer.class);
	
	@Autowired
	ApplicationContext _ctx;
	
	public AnnotationObjects() {
		
	}
	
	public  Object GetImplement(Class<? extends Annotation> annotationType_) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		if (_ctx != null)
		{
			String[] names = _ctx.getBeanNamesForAnnotation(annotationType_);
			
			 for (String beanName : names) {
				 return _ctx.getType(beanName).getDeclaredConstructor().newInstance();
	         }		
		}
		else
		{
			_log.error("ApplicationContext is null");
		}
		return null;
	}
}
