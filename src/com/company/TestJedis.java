package com.company;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

/**
 * jedis操作数据
 *
 * Created by lishanglai on 2017/11/10.
 */
public class TestJedis {

    @Test
    public void testSetString(){
        Jedis jedis = RedisTools.getJedis();
        String set = jedis.set("test","1234");
        System.out.println(set);
        RedisTools.closeJedis(jedis);
    }


    @Test
    public void getGetString(){
        Jedis jedis = RedisTools.getJedis();
        String s = jedis.get("test");
        System.out.println(s);
        RedisTools.closeJedis(jedis);
    }

    /**
     * 集群模式
     */
    @Test
    public void testShardJedisSet(){

        RedisTools.createJedisSharedPool();
        ShardedJedis sharedJedis = RedisTools.getSharedJedis();
        sharedJedis.sadd("1","aaa");
        sharedJedis.sadd("2","aaa");
        sharedJedis.sadd("3","aaa");
        sharedJedis.sadd("4","aaa");
        sharedJedis.sadd("5","aaa");
        sharedJedis.sadd("6","aaa");
        sharedJedis.sadd("aaa","aaa");
        sharedJedis.sadd("vvvv","aaa");
        sharedJedis.sadd("bbbb","aaa");
        sharedJedis.sadd("nnnn","aaa");
        sharedJedis.sadd("ffff","aaa");
        sharedJedis.sadd("htyhtht","aaa");
        sharedJedis.sadd("lmlkmk","aaa");
        sharedJedis.sadd("454545","aaa");
        RedisTools.closeSharedJedis(sharedJedis);

    }

    @Test
    public void testShardJedisGet(){
        RedisTools.createJedisSharedPool();
        ShardedJedis sharedJedis = RedisTools.getSharedJedis();
        String s = sharedJedis.get("1");
        System.out.println(s);
        RedisTools.closeSharedJedis(sharedJedis);
    }

    @Test
    public void test() {
        System.out.println(Hashing.MD5.hash("192.168.232.132")%Integer.MAX_VALUE);
        System.out.println(Hashing.MD5.hash("1")%Integer.MAX_VALUE);
        System.out.println(Hashing.MD5.hash("6")%Integer.MAX_VALUE);
    }
}
