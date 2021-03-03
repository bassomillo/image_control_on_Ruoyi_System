//package com.ruoyi.project.common;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 图片绝对地址与虚拟地址映射
// * @author caoxr
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    @Value("${file.staticAccessPath}")
//    private String staticPath;
////    private static String staticPath = "/upload/**";
//
//    @Value("${spring.servlet.multipart.location}")
//    private String path;
////    private static String path = "E:/upload";//真实地址
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler(staticPath).addResourceLocations("file:" + path + "/");
////        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/upload/");
//    }
//}
