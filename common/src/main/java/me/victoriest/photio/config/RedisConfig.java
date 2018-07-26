package me.victoriest.photio.config;

import me.victoriest.photio.cache.PermanentRedisCache;
import me.victoriest.photio.cache.RedisCache;
import me.victoriest.photio.cache.serializer.HessianRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 *
 * @author VictoriEST
 * @date 2017/5/8
 * spring-boot-server
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@ConditionalOnClass(RedisClusterConfigurationProperties.class)
//@EnableConfigurationProperties(RedisClusterConfigurationProperties.class)
public class RedisConfig {
    /**
     * Type safe representation of application.properties
     */
    @Autowired
    RedisClusterConfigurationProperties clusterProperties;

    public static class Pool {
        private Integer maxTotal;
        private Integer maxIdle;
        private Integer minIdle;
        private Integer maxWaitMillis;
        private Integer minEvictableIdleTimeMillis;
        private Integer timeBetweenEvictionRunsMillis;

        public Integer getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(Integer maxTotal) {
            this.maxTotal = maxTotal;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(Integer minIdle) {
            this.minIdle = minIdle;
        }

        public Integer getMaxWaitMillis() {
            return maxWaitMillis;
        }

        public void setMaxWaitMillis(Integer maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
        }

        public Integer getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public Integer getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }
    }

    private Integer timeout;

    private String host;

    private Integer port;

    private String password;

    private Integer database;

    private Integer timeLimit;

    private Pool pool = new Pool();

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
//        JedisConnectionFactory j = new JedisConnectionFactory(
//                new RedisClusterConfiguration(clusterProperties.getNodes()),
//                jedisPoolConfig());
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(database);

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(timeout));
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;

    }

////
//    @Bean
//    public JedisPool redisPoolFactory() {
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig(), "192.168.203.249",
//                7000,
//                50000,
//                "");
//        return jedisPool;
//    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal((pool.getMaxTotal()));
        poolConfig.setMaxIdle((pool.getMaxIdle()));
        poolConfig.setMinIdle((pool.getMinIdle()));
        poolConfig.setMaxWaitMillis((pool.getMaxWaitMillis()));
        poolConfig.setMinEvictableIdleTimeMillis((pool.getMinEvictableIdleTimeMillis()));
        poolConfig.setTimeBetweenEvictionRunsMillis((pool.getTimeBetweenEvictionRunsMillis()));
        return poolConfig;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public HessianRedisSerializer hessianRedisSerializer() {
        return new HessianRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate r = new RedisTemplate();
        r.setConnectionFactory(factory);
        r.setKeySerializer(stringRedisSerializer());
        r.setValueSerializer(hessianRedisSerializer());
        r.setHashKeySerializer(hessianRedisSerializer());
        r.setHashValueSerializer(hessianRedisSerializer());

        return r;
    }

    @Bean
    public RedisCache redisCache(RedisTemplate redisTemplate) {
        RedisCache redisCache = new RedisCache(redisTemplate, timeLimit);
        return redisCache;
    }

    @Bean
    public PermanentRedisCache premanentRedisCache(RedisTemplate redisTemplate) {
        PermanentRedisCache redisCache = new PermanentRedisCache(redisTemplate);
        return redisCache;
    }

//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useClusterServers()
//                .setReadMode(ReadMode.MASTER_SLAVE)
//                .setSubscriptionMode(SubscriptionMode.MASTER)
//                .setMasterConnectionPoolSize(pool.getMaxTotal())
//                .setMasterConnectionMinimumIdleSize(pool.getMinIdle())
//                .setIdleConnectionTimeout(pool.getMaxWaitMillis())
//                .setPassword(password)
//                .setTimeout(timeout);
//
//        for(String s : clusterProperties.getNodes()) {
//            config.useClusterServers().addNodeAddress("redis://" + s);
//        }
//
//        RedissonClient redisson = Redisson.create(config);
//        return redisson;
//    }


//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        // just used StringRedisTemplate for simplicity here.
//        return new StringRedisTemplate(factory);
//    }

//    @Bean
//    public JedisCluster redisCluster(JedisPoolConfig jedisPoolConfig) {
//        Set<HostAndPort> nodes = new HashSet<>();
//        for (String node:clusterProperties.getNodes()){
//            String[] parts= StringUtils.split(node,":");
//            Assert.state(parts.length==2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
//            nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
//        }
//        return new JedisCluster(nodes, jedisPoolConfig);
//    }

}
