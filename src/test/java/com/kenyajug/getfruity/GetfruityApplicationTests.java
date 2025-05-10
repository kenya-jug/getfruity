package com.kenyajug.getfruity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
@SpringBootTest
class GetfruityApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;
	@Test
	void contextLoads() {
		Assertions.assertThat(applicationContext).isNotNull();
	}
	@Test
	void mainApplicationClassLoads() {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Assertions.assertThat(beanNames).isNotEmpty();
		Assertions.assertThat(beanNames).contains("getfruityApplication");
	}
	@Test
	void mainMethodRunsWithoutException() {
		Assertions.assertThatCode(() -> GetfruityApplication.main(new String[]{})).doesNotThrowAnyException();
	}
}
