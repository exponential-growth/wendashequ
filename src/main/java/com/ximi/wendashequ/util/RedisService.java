package com.ximi.wendashequ.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
public class RedisService implements InitializingBean {
    // 记录日志信息，无关紧要
    private final static Logger log = LoggerFactory.getLogger(RedisService.class);
    //
    private JedisPool pool;

    //1、初始化redis连接池
    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/1");

    }
    //2、新增数据
    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key,value);
        }catch (Exception e){
            log.error("赞异常",e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }
    //3、删除数据
    public long srem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key,value);
        }catch (Exception e){
            log.error("踩异常",e.getMessage().toString());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    //4、查询所有数据
    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            log.error("获取数量出现异常",e.getMessage().toString());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }
    //5、查询是否存在
    public boolean sismember(String key,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            log.error("判断是否赞过出现异常",e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return false;
    }
}
