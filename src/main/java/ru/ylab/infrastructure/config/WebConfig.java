package ru.ylab.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@EnableAspectJAutoProxy
@EnableWebMvc
@Import(OpenApiConfig.class)
@ComponentScan(basePackages = {"ru.ylab.adapters.in.web.controller"})
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //TODO delete
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
//                .indentOutput(true);
//        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//}

//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.mediaType("json", MediaType.APPLICATION_JSON);
//    }
}
