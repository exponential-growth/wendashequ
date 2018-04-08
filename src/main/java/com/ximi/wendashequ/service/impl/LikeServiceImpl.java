package com.ximi.wendashequ.service.impl;

import com.ximi.wendashequ.service.LikeService;
import com.ximi.wendashequ.util.RedisKeyUtil;
import com.ximi.wendashequ.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisService redisService;

    @Override
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        return redisService.scard(likeKey);
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if (redisService.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getLikeKey(entityType,entityId);

        return redisService.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId, int entityType, int entityId){

       String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);

       redisService.sadd(likeKey,String.valueOf(userId));
       String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);
       redisService.srem(disLikeKey,String.valueOf(userId));

       return redisService.scard(likeKey);
    }

    @Override
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType,entityId);

        redisService.sadd(disLikeKey,String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        redisService.srem(likeKey,String.valueOf(userId));

        return redisService.scard(likeKey);
    }


}
