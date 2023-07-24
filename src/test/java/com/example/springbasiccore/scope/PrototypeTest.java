package com.example.springbasiccore.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @DisplayName("스프링 컨테이너의 prototype 빈 생성에 대한 동일성 검증 ")
    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            PrototypeBean.class);

        System.out.println("find prototypebean1");
        PrototypeBean pototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypebean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("singletonBean1 = " + pototypeBean1);
        System.out.println("singletonBean2 = " + prototypeBean2);

        assertThat(pototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    }

    // @ComponentScan 이 없어도 AnnotationConfigApplicationContext 에 클래스를 인자로 넘김으로써
    // Bean 으로 등록함
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }

}
