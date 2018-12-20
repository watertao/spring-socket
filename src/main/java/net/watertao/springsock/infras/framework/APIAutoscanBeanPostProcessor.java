package net.watertao.springsock.infras.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class APIAutoscanBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(APIAutoscanBeanPostProcessor.class);

    private static final String API_METHOD_NAME = "call";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean == null) {
            return bean;
        }

        if (!bean.getClass().isAnnotationPresent(API.class)) {
            return bean;
        }

        if (!AbstractAPIService.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException("@API annotation should only be assigned on AbstractAPIService");
        }

        API apiAnnotation = bean.getClass().getAnnotation(API.class);
        String functionCode = apiAnnotation.value();

        Method[] methods = bean.getClass().getDeclaredMethods();
        Method callMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(API_METHOD_NAME) && !method.isBridge()) {
                callMethod = method;
                break;
            }
        }

        Class requestType = callMethod.getParameterTypes()[0];

        logger.info("REGISTER API: " + functionCode + " - " + bean.getClass().getName());
        APIMetadataHolder.registerApi(functionCode, (AbstractAPIService) bean, requestType);

//        Parameter[] parameters = method.getParameters();
//        System.out.println(parameters[0]);


//        Field[] fields = bean.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            if(field.isAnnotationPresent(Dsrv.class)) {
//                String fieldName = field.getName();
//                Class dsrvType = field.getType();
//                field.setAccessible(true);
//                try {
//                    logger.info("[rsrv-dubbo] inject " + dsrvType.getSimpleName() + " to bean " + beanName);
//                    field.set(bean, dsrvFactory.getDsrv(dsrvType));
//                } catch (IllegalAccessException e) {
//                    throw new BeanInstantiationException(bean.getClass(),e.getMessage(), e);
//                }
//            }
//        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
