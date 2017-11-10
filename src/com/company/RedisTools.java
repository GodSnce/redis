package com.company;

import redis.clients.jedis.*;
import redis.clients.util.Hashing;

import java.util.ArrayList;

/**
 * Created by lishanglai on 2017/11/10.
 */
public class RedisTools {

    public static int SECONDS = 3600 * 24;//为key指定过期时间 单位是s

    private static JedisPool pool;
    private static ShardedJedisPool sharedPool;


    static {
        //jedis配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(1000);//最大空闲
        config.setMaxTotal(10240);//最大连接数
        if (pool == null){
            //
            pool = new JedisPool(config,"192.168.242.132",6379,0);
        }
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void closeJedis(Jedis jedis){
        pool.returnResource(jedis);
    }


    public static void createJedisSharedPool(){
        JedisPoolConfig config = new JedisPoolConfig();//jedis池配置
        config.setMaxIdle(1000);//对象最大空闲时间
        config.setMaxWaitMillis(1000 * 10);//获得对象时最大等待时间
        config.setMinIdle(0);//对象最小空闲时间
        config.setTestOnBorrow(false);//在池中取出连接前进行检验
        ArrayList<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
        JedisShardInfo info = new JedisShardInfo("192.168.242.132",6379);
        info.setTimeout(1000 * 10);
        list.add(info);
        sharedPool = new ShardedJedisPool(config,list, Hashing.MD5);

    }

    public static ShardedJedis getSharedJedis(){
        return sharedPool.getResource();
    }

    public static void closeSharedJedis(ShardedJedis jedis){
        sharedPool.returnResource(jedis);
    }
}
