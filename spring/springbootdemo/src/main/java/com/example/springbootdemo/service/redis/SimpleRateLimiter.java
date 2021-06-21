package com.example.springbootdemo.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

//redis 简单限流
//系统要限定用户的某个行为在指定的时间里只能允许发生 N 次，

public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }
    //调用这个接口 , 一分钟内只允许最多回复 5 个帖子
    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        //同一个用户同一种行为用一个 zset 记录
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        Pipeline pipe = jedis.pipelined();
        pipe.multi(); //事务开始
        pipe.zadd(key, nowTs, "" + nowTs); //Redis ZADD 命令用于将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
        //移除时间窗口之前的行为记录，剩下的都是时间窗口内的
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);//命令移除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员。
        //获取窗口内的行为数量
        Response<Long> count = pipe.zcard(key); //用于返回有序集的成员个数。
        pipe.expire(key, period + 1); //过期时间
        pipe.exec(); //事务结束
        try {
            pipe.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count.get() <= maxCount;
    }

    public void Test(){
        System.out.println("xxx");

        for(int i=0;i<20;i++) {
            System.out.println(isActionAllowed("laoqian", "reply", 60, 5));
        }
    }
}
