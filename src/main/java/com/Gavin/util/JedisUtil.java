package com.Gavin.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: Gavin
 * @description:
 * @className: JedisUtil
 * @date: 2022/3/4 14:39
 * @version:0.1
 * @since: jdk14.0
 */
public class JedisUtil {

    private static JedisPool jedisPool;

    static{
        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMaxIdle(10);

        jedisPool=new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
