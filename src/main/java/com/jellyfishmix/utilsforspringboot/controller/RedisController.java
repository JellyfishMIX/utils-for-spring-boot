package com.jellyfishmix.utilsforspringboot.controller;

import com.jellyfishmix.utilsforspringboot.service.RedisService;
import com.jellyfishmix.utilsforspringboot.utils.ResultVOUtil;
import com.jellyfishmix.utilsforspringboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

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
     * 测试redis记录keyword
     *
     * @param keyword 搜索关键词
     * @return
     */
    @GetMapping("/test_search")
    public ResultVO testSearch(@RequestParam("keyword") String keyword) {
        redisService.searchZincrby(keyword);
        return ResultVOUtil.success(1, "test-return");
    }

    /**
     * 测试redis查询指定范围的热词
     *
     * @param start 查询范围开始位置
     * @param end 查询范围结束位置
     * @return
     */
    @GetMapping("/test_query_top_search_hot_word")
    public ResultVO testQueryTopSearchHotWord(@RequestParam("start") Integer start,
                                              @RequestParam("end") Integer end) {
        Set<ZSetOperations.TypedTuple<String>> resultSet =  redisService.queryTopSearchHotWord(start, end);
        return ResultVOUtil.success(1, "success", resultSet);
    }

    /**
     * 删除指定的key
     *
     * @param keyName keyName
     * @return
     */
    @PostMapping("/delete_key")
    public ResultVO deleteKey(@RequestParam("keyName") String keyName) {
        redisService.deleteKey(keyName);
        return ResultVOUtil.success(1, "test-return");
    }

}
