package com.jellyfishmix.utilsforspringboot.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 4:04 下午
 */
public interface RedisService {

    // Sorted Set

    // 解析：[redis 实现搜索热词统计](https://juejin.im/post/5ed736dce51d45784f800dda)

    /**
     * 记录查询热词
     * zincrby命令，对于一个Sorted Set，存在的就把分数加1，不存在就创建一个分数为1的成员
     *
     * @param keyword 关键词
     */
    void searchZincrby(String keyword);

    /**
     * 查询集合中指定顺序的值，zrevrange
     * 返回有序的集合中，score大的在前面
     *
     * @param start 查询范围开始位置
     * @param end 结束位置
     * @return
     */
    Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotKey(Integer start, Integer end);

}
