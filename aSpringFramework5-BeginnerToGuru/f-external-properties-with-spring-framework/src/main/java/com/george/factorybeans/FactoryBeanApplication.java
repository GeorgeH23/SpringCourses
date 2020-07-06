package com.george.factorybeans;

import com.george.factorybeans.controllers.MyController;
import com.george.factorybeans.examplebeans.FakeDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FactoryBeanApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FactoryBeanApplication.class, args);

		MyController controller = (MyController) ctx.getBean("myController");

		FakeDataSource fakeDataSource = (FakeDataSource) ctx.getBean(FakeDataSource.class);

		System.out.println(fakeDataSource.getUsername());

	}
}
