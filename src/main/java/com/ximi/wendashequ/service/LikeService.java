package com.ximi.wendashequ.service;

public interface LikeService {
    //获取点赞数
    public long getLikeCount(int entityType,int EntityId);
    //获取不喜欢的数
    public int getLikeStatus(int userId,int entityType,int entityId);
    //点赞
    public long like(int userId,int entityType,int entityId);
    //不点赞
    public long disLike(int userId,int entityType,int entityId);
}
