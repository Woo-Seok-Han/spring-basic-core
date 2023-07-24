package com.example.springbasiccore.scope;

import static org.assertj.core.api.Assertions.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototype {

    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            PrototpyeBean.class);

        PrototpyeBean prototpyeBean1 = ac.getBean(PrototpyeBean.class);
        prototpyeBean1.addCount();
        assertThat(prototpyeBean1.getCount()).isEqualTo(1);

        PrototpyeBean prototpyeBean2 = ac.getBean(PrototpyeBean.class);
        prototpyeBean2.addCount();
        assertThat(prototpyeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            ClientBean.class, PrototpyeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean {

        private final PrototpyeBean prototpyeBean;

        public int logic() {
            prototpyeBean.addCount();
            return prototpyeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototpyeBean {
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototpyeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototpyeBean.destroy");
        }
    }

}
