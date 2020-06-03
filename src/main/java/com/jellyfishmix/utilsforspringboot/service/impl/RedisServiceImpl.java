package com.jellyfishmix.utilsforspringboot.service.impl;

import com.jellyfishmix.utilsforspringboot.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 4:04 下午
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 记录查询热词
     * zincrby命令，对于一个Sorted Set，存在的就把分数加x(x可自行设定)，不存在就创建一个分数为1的成员
     *
     * @param keyword 搜索关键词
     */
    @Override
    public void searchZincrby(String keyword) {
        redisTemplate.opsForZSet().incrementScore("searchHotKey", keyword, 1.0);
    }

    /**
     * zrevrange命令, 查询集合中指定顺序的值
     * 返回有序的集合中，score大的在前面
     *
     * @param start 查询范围开始位置
     * @param end 查询范围结束位置
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotKey(Integer start, Integer end) {
        Set<ZSetOperations.TypedTuple<String>> resultSet =  redisTemplate.opsForZSet().reverseRangeWithScores("searchHotKey", start, end);
        return resultSet;
    }
}
