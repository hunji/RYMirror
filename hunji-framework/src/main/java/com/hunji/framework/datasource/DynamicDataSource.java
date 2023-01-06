package com.hunji.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author hunji
 * @version 1.0
 * @date 2022/12/20 16:50
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources){
        /**
         *  设置默认数据源
         */
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        /**
         * 全部数据源
         */
        super.setTargetDataSources(targetDataSources);
        /**
         * 根据名称查找数据源
         */
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
