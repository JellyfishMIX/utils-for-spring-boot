package com.jellyfishmix.utilsforspringboot.controller;

import com.jellyfishmix.utilsforspringboot.service.RedisService;
import com.jellyfishmix.utilsforspringboot.utils.ResultVOUtil;
import com.jellyfishmix.utilsforspringboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 4:06 下午
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    /**
     * 测试redis记录HotKey
     *
     * @param keyword 搜索关键词
     * @return
     */
    @GetMapping("/test_hot_key")
    public ResultVO testHotKey(@RequestParam("keyword") String keyword) {
        redisService.searchZincrby(keyword);
        return ResultVOUtil.success(1, "test-return");
    }

    /**
     * 测试redis查询指定范围的热词
     *
     * @param start 查询范围开始位置
     * @param end 结束位置
     * @return
     */
    @GetMapping("/test_query_top_hot_key")
    public ResultVO testQueryTopHotKey(@RequestParam("start") Integer start,
                                       @RequestParam("end") Integer end) {
        Set<ZSetOperations.TypedTuple<String>> resultSet =  redisService.queryTopSearchHotKey(start, end);
        return ResultVOUtil.success(1, "success", resultSet);
    }
}
