package com.itheone.servlet;

import com.itheone.others.OrderFilter;
import com.itheone.others.OrderListener;
import com.itheone.others.OrderServlet;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes(value={JamesService.class})
public class JamesServletContainerInitializer implements ServletContainerInitializer{
	//arg0:父类感兴趣的类的子类型
	//ServletContext arg1:....代表当前web应用,注册3组件
	@Override
	public void onStartup(Set<Class<?>> arg0, ServletContext arg1) throws ServletException {

		System.out.println("感兴趣的类型");
		for(Class<?> claz:arg0){
			System.out.println(claz);// 反射
		}
		//注册OrderServlet组件   组件方式添加servlet（区分JamesServlet注解式）
		javax.servlet.ServletRegistration.Dynamic servlet = arg1.addServlet("orderServlet", new OrderServlet());
		servlet.addMapping("/orderTest");
		
		//注册监听器listener
		arg1.addListener(OrderListener.class);
		
		//注册filter
		javax.servlet.FilterRegistration.Dynamic filter = arg1.addFilter("orderFilter", OrderFilter.class);
		//添加filer的映射信息，可以指定专门来拦截哪个servlet
		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		
	}

}
