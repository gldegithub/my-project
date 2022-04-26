package org.leo.energy.service.boot.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:gonglong
 * @Date:2022/4/11 16:55
 */
@Data
@ConfigurationProperties(prefix = "leo.mybatis-plus")
public class MybatisPlusProperties {

    /**
     * 分页最大数
     */
    private Long pageLimit = 500L;

    /**
     * 溢出总页数后是否进行处理
     */
    protected Boolean overflow = false;
}
