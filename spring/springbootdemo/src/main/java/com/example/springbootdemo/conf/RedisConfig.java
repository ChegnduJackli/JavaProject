package com.example.springbootdemo.conf;

import com.example.springbootdemo.common.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
@PropertySource("classpath:config/redis.properties")
@EnableAutoConfiguration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;

    /*
    * jedis配置
    * jedisPool.getResource(); 这样即可拿到jedis实例
    * */
    @Bean
    public JedisPool redisPoolFactory()  throws Exception{
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
        return jedisPool;
    }

//    @Bean
//    public JedisPoolConfig getRedisConfig(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(maxIdle);
//        config.setMaxWaitMillis(maxWaitMillis);
//        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
//        config.setBlockWhenExhausted(blockWhenExhausted);
//        // 是否启用pool的jmx管理功能, 默认true
//        config.setJmxEnabled(true);
//        return config;
//    }
//
//
//    @Bean
//    @ConfigurationProperties(prefix="spring.redis")
//    public JedisConnectionFactory getConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        JedisPoolConfig config = getRedisConfig();
//        factory.setPoolConfig(config);
//        log.info("JedisConnectionFactory bean init success.");
//        return factory;
//
//    }
//    @Bean
//    public RedisTemplate<?, ?> getRedisTemplate(){
//        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory()); //只能对字符串的键值操作
//        System.out.println("生成<?,?>template:" + template);
//        return template;
//    }
//
//    @Bean(name = "LongRedisTemplate")
//    public RedisTemplate<String, Long> getRedisTemplate1(){
//        RedisTemplate<String,Long> template = new RedisTemplate<String,Long>(); //只能对字符串的键值操作
//        template.setConnectionFactory(getConnectionFactory());
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        template.setKeySerializer(jackson2JsonRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//
//        System.out.println("生成<String, Long>template:" + template);
//        return template;
//    }
//
//    @Bean(name = "StringRedisTemplate")
//    public RedisTemplate<String, String> getRedisTemplate2(){
//        RedisTemplate<String,String> template = new RedisTemplate<String,String>(); //只能对字符串的键值操作
//        template.setConnectionFactory(getConnectionFactory());
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        template.setKeySerializer(jackson2JsonRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//
//        System.out.println("生成<String, String>template:" + template);
//        return template;
//    }
//
//    @Bean(name = "IntegerRedisTemplate")
//    public RedisTemplate<String, Integer> getRedisTemplate3(){
//        RedisTemplate<String,Integer> template = new RedisTemplate<String,Integer>(); //只能对字符串的键值操作
//        template.setConnectionFactory(getConnectionFactory());
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        template.setKeySerializer(jackson2JsonRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//
//        System.out.println("生成<String,Integer>template:" + template);
//        return template;
//    }

    //自定义 RedisTemplate 序列化方式
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();//创建 RedisTemplate，key 和 value 都采用了 Object 类型
        redisTemplate.setConnectionFactory(redisConnectionFactory);//绑定 RedisConnectionFactory

        //创建 Jackson2JsonRedisSerializer 序列方式，对象类型使用 Object 类型，
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);//设置一下 jackJson 的 ObjectMapper 对象参数

        // 设置 RedisTemplate 序列化规则。因为 key 通常是普通的字符串，所以使用 StringRedisSerializer 即可。
        // 而 value 是对象时，才需要使用序列化与反序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());// key 序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value 序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());// hash key 序列化规则
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// hash value 序列化规则
        redisTemplate.afterPropertiesSet();//属性设置后操作
        return redisTemplate;//返回设置好的 RedisTemplate
    }



}
