package com.cxx.concurrent.lock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import redis.clients.jedis.Jedis;


@RunWith(JUnit4.class)
public class DistributeLockTest {

    private static Jedis jedis = new Jedis();
    private static final Logger logger = Logger.getLogger(DistributeLock.class);
    private static final String lockKey = "lock";
    static{
        jedis.auth("a1057791823");
    }
    @Test
    public void testDistributeLock() throws InterruptedException {
        new Thread(()->{
            if(DistributeLock.tryAndGetDistributeLock(jedis,lockKey,Thread.currentThread().getId()+"",10000))
                System.out.println(Thread.currentThread().getName()+"加锁成功");
            else
                System.out.println(Thread.currentThread().getName()+"加锁失败");
        }).start();
        if(DistributeLock.tryAndGetDistributeLock(jedis,lockKey,Thread.currentThread().getId()+"",10000))
            System.out.println(Thread.currentThread().getName()+"加锁成功");
        else
            System.out.println(Thread.currentThread().getName()+"加锁失败");
        if(DistributeLock.releaseDistributeLock(jedis,lockKey,Thread.currentThread().getId()+""))
            System.out.println(Thread.currentThread().getName()+"解锁成功");
        else
            System.out.println(Thread.currentThread().getName()+"解锁失败");
    }
}