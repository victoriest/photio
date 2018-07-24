package me.victoriest.photio.config;

import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 *
 * @author VictoriEST
 * @date 2017/6/2
 * spring-boot-server
 */
@Configuration
@EnableTransactionManagement
@MapperScan("me.victoriest.photio.mapper.source")
public class MybatisConfig {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        OffsetLimitInterceptor interceptor=new OffsetLimitInterceptor();
        interceptor.setDialectClass("com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");

        //添加插件
        bean.setPlugins(new Interceptor[]{interceptor});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            System.out.println("\n\n\n\n\n\n"+mapperLocations);
            bean.setMapperLocations(resolver.getResources(mapperLocations));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
