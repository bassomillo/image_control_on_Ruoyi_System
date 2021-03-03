package com.ruoyi.framework.converters;

import com.ruoyi.common.utils.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义LocalDateTime相关的参数转换器 LocalDate格式为：yyyy-MM-dd; LocalTime格式为：HH:mm:ss; LocalDateTime格式为：yyyy-MM-dd HH:mm:ss
 */
@Configuration
public class LocalDateTimeConverters {

  @Bean
  public Converter<String, LocalDate> localDateConverter() {
    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(String source) {
        if (StringUtils.isBlank(source)) {
          return null;
        } else {
          return LocalDate.parse(source, DateTimeFormatter.ofPattern(LocalDateTimePattern.LOCAL_DATE));
        }
      }
    };
  }

  @Bean
  public Converter<String, LocalTime> localTimeConverter() {
    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(String source) {
        if (StringUtils.isBlank(source)) {
          return null;
        } else {
          return LocalTime.parse(source, DateTimeFormatter.ofPattern(LocalDateTimePattern.LOCAL_TIME));
        }
      }
    };
  }

  @Bean
  public Converter<String, LocalDateTime> localDateTimeConverter() {
    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(String source) {
        if (StringUtils.isBlank(source)) {
          return null;
        } else {
          return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(LocalDateTimePattern.LOCAL_DATE_TIME));
        }
      }
    };
  }
}
