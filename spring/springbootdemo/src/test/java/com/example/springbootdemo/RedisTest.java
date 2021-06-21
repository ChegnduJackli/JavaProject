package com.example.springbootdemo;

import com.example.springbootdemo.common.JedisUtil;
import com.example.springbootdemo.common.RedisUtil;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.Dto.OrgDto;
import com.example.springbootdemo.entity.User;
//import com.example.springbootdemo.service.redis.SimpleRateLimiter;
import com.example.springbootdemo.service.redis.FunnelRateLimiter;
import com.example.springbootdemo.service.redis.SimpleRateLimiter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;





    @Autowired
    private RedisUtil _redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    JedisUtil jedisUtil;

//    @Autowired
//    private SimpleRateLimiter _simpleRateLimiter;

    @Test
    public void testJedis() {

     //   new SimpleRateLimiter (jedisUtil.getRedis()).Test();

        new FunnelRateLimiter().TestMain();

       String value = jedisUtil.get("key1",0);
        jedisUtil.set("key3","test3",0);
        String value3 = jedisUtil.get("key3",0);
        System.out.println(value);
    }

    @Test
    public void testJedisPipeLine() {
        Jedis redis = jedisUtil.getRedis();
        redis.select(8);//使用第8个库做测试

        redis.flushDB();//清空第8个库所有数据
        Map<String, String> data = new HashMap<String, String>();
        long start = System.currentTimeMillis();
        // 直接hmset
        for (int i = 0; i < 10000; i++) {
            data.clear();  //清空map
            data.put("k_" + i, "v_" + i);
            redis.hmset("key_" + i, data); //循环执行10000条数据插入redis
        }
        long end = System.currentTimeMillis();
        System.out.println("    共插入:[" + redis.dbSize() + "]条 .. ");
        System.out.println("1,未使用PIPE批量设值耗时" + (end - start) / 1000 + "秒..");

        redis.select(8);
        redis.flushDB();

        Pipeline pipe = redis.pipelined();
        start = System.currentTimeMillis();
        //
        for (int i = 0; i < 10000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            pipe.hmset("key_" + i, data); //将值封装到PIPE对象，此时并未执行，还停留在客户端
        }
        pipe.sync(); //将封装后的PIPE一次性发给redis
        end = System.currentTimeMillis();
        System.out.println("    PIPE共插入:[" + redis.dbSize() + "]条 .. ");
        System.out.println("2,使用PIPE批量设值耗时" + (end - start) / 1000 + "秒 ..");

        //---------------------------------------------------------------------------------------------
        // hmget
        Set<String> keys = redis.keys("key_*"); //将上面设值所有结果键查询出来
        // 直接使用Jedis hgetall
        start = System.currentTimeMillis();
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (String key : keys) {
            //此处keys根据以上的设值结果，共有10000个，循环10000次
            result.put(key, redis.hgetAll(key)); //使用redis对象根据键值去取值，将结果放入result对象
        }
        end = System.currentTimeMillis();
        System.out.println("    共取值:[" + redis.dbSize() + "]条 .. ");
        System.out.println("3,未使用PIPE批量取值耗时 " + (end - start) / 1000 + "秒 ..");

        // 使用pipeline hgetall
        result.clear();
        start = System.currentTimeMillis();
        for (String key : keys) {
            pipe.hgetAll(key); //使用PIPE封装需要取值的key,此时还停留在客户端，并未真正执行查询请求
        }
        pipe.sync();  //提交到redis进行查询

        end = System.currentTimeMillis();
        System.out.println("    PIPE共取值:[" + redis.dbSize() + "]条 .. ");
        System.out.println("4,使用PIPE批量取值耗时" + (end - start) / 1000 + "秒 ..");

    }
    @Test
    public void testRedisTempate() {
        
        Object key1 = _redisUtil.get("key1");
        _redisUtil.set("key2","utilsSet");

        Object key2 = _redisUtil.get("key2");

        String key11 = redisTemplate.boundValueOps("key1").get();
        Boolean key12 = redisTemplate.hasKey("key1");

        redisTemplate.opsForValue().set("key1", "from java program");


        Test2();
        redisSpeed();
        //1,从redis获取数据，json字符串
        String userListJson = redisTemplate.boundValueOps("user.findAll").get();

        //2,判断redis是否存在数据
        if(userListJson ==null){
            //3,不存在数据，从数据库中查询
            List<User> all = userMapper.findAll();
            //4，将数据存在redis缓存中
            //向将list集合转换为json格式,使用jackson进行转换
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                userListJson=  objectMapper.writeValueAsString(all);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            redisTemplate.boundValueOps("user.findAll").set(userListJson);
            System.out.println("----从数据库中获取User数据---");
        }
        else{
            System.out.println("----从Redis缓存中获取User数据---");
        }
        System.out.println(userListJson);

        //4,将数据在控制台打印
    }

    private void Test2() {
        String key = "hash";
        Map<String, String> map = new HashMap<String, String>();
        map.put("f1", "val1");
        map.put("f2", "val2");
        // 相当于hmset命令
        redisTemplate.opsForHash().putAll(key, map);
        // 相当于hset命令
        redisTemplate.opsForHash().put(key, "f3", "6");
        printValueForhash(redisTemplate, key, "f3");
        // 相当于 hexists key filed 命令
        boolean exists = redisTemplate.opsForHash().hasKey(key, "f3");
        System.out.println(exists);
    }

    private void printValueForhash(RedisTemplate redisTemplate, String key, String field) {
        //相当于hget命令
        Object value = redisTemplate.opsForHash().get(key, field);
        System.out.println(value);
    }
    private  void redisSpeed(){
        int i = 0;
        try {
            long start = System.currentTimeMillis(); // 开始毫秒数
            while (true) {
                long end = System.currentTimeMillis();
                if (end - start >= 1000) {
                    // 当大于等于1000毫秒（相当于1秒）时，结束操作
                    break;
                }
                i++;
                redisTemplate.opsForValue().set("test" + i, i + "");
            }
        } finally {
            // 关闭连接

        }
        // 打印1秒内对Redis的操作次数
        System.out.println("reids每秒操作：" + i + "次");
    }


}
