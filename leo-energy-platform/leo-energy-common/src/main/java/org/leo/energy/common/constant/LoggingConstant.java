package org.leo.energy.common.constant;

/**
 * @Author:gonglong
 * @Date:2022/4/12 10:26
 */
public interface LoggingConstant {

    String LOGBACK_CONFIG = "classpath:logback/logback_${spring.profiles.active}.xml";
    String LOG4J2_CONFIG = "classpath:log4j2/log4j2_${spring.profiles.active}.xml";

}
