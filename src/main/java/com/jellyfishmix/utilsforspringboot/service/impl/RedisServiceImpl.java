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
     * 记录查询keyword
     * zincrby命令，对于一个Sorted Set，存在的就把分数加x(x可自行设定)，不存在就创建一个分数为1的成员
     *
     * @param keyword 搜索关键词
     */
    @Override
    public void searchZincrby(String keyword) {
        // 名为"sortedSetName"的Sorted Set不用预先创建，不存在会自动创建，存在则向里添加数据
        String sortedSetName = "searchHotWord";
        // x 的含义请见本方法的注释
        double x = 1.0;
        redisTemplate.opsForZSet().incrementScore(sortedSetName, keyword, x);
    }

    /**
     * zrevrange命令, 查询Sorted Set中指定范围的值
     * 返回的有序集合中，score大的在前面
     * zrevrange方法无需担心用于指定范围的start和end出现越界报错问题
     *
     * @param start 查询范围开始位置
     * @param end 查询范围结束位置
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotWord(Integer start, Integer end) {
        String sortedSetName = "searchHotWord";
        Set<ZSetOperations.TypedTuple<String>> resultSet =  redisTemplate.opsForZSet().reverseRangeWithScores(sortedSetName, start, end);
        return resultSet;
    }

    /**
     * 删除指定的key
     *
     * @param keyName keyName
     */
    @Override
    public void deleteKey(String keyName) {
        redisTemplate.delete(keyName);
    }

}
