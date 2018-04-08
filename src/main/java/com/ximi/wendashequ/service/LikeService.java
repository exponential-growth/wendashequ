package com.ximi.wendashequ.service;

public interface LikeService {
    public long getLikeCount(int entityType,int EntityId);
    public int getLikeStatus(int userId,int entityType,int entityId);
    public long like(int userId,int entityType,int entityId);
    public long disLike(int userId,int entityType,int entityId);
}
