package com.example.springbasiccore.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

@SpringBootTest
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
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean {

        private final ObjectProvider<PrototpyeBean> prototpyeBeanObjectProvider;
//        private final PrototpyeBean prototpyeBean;

        public int logic() {
//            prototpyeBean.addCount();
//            return prototpyeBean.getCount();
            PrototpyeBean prototpyeBean = prototpyeBeanObjectProvider.getObject();
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
