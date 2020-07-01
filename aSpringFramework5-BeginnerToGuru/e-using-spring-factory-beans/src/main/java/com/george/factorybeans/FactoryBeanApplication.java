package com.george.factorybeans;

import com.george.factorybeans.controllers.ConstructorInjectedController;
import com.george.factorybeans.controllers.GetterInjectedController;
import com.george.factorybeans.controllers.MyController;
import com.george.factorybeans.controllers.PropertyInjectedController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FactoryBeanApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FactoryBeanApplication.class, args);

		MyController controller = (MyController) ctx.getBean("myController");

		System.out.println(controller.hello());
		System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
		System.out.println(ctx.getBean(GetterInjectedController.class).sayHello());
		System.out.println(ctx.getBean(ConstructorInjectedController.class).sayHello());
	}
}
