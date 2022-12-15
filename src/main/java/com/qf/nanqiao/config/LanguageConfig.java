package com.qf.nanqiao.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.validation.Validator;
import java.util.Locale;

import static java.util.Arrays.asList;

/**
 * 语言配置
 *
 * @author zed
 * @date 2022/12/06
 */
@Configuration
public class LanguageConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //指定国际化的Resource Bundle地址
        messageSource.setBasename("ValidateMessage");
        //指定国际化的默认编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        // 支持中文和英文
        resolver.setSupportedLocales(asList(Locale.CHINA, Locale.US,Locale.TAIWAN));
        // 默认使用的语言是中文
        resolver.setDefaultLocale(Locale.CHINA);
        return resolver;
    }
}
