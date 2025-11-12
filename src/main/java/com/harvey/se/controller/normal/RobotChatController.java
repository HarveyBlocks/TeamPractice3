package com.harvey.se.controller.normal;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.ChatMessageDto;
import com.harvey.se.pojo.dto.ChatTextPiece;
import com.harvey.se.pojo.vo.DateRange;
import com.harvey.se.pojo.vo.Result;
import com.harvey.se.properties.ConstantsProperties;
import com.harvey.se.service.ChatMessageService;
import com.harvey.se.service.RobotChatService;
import com.harvey.se.util.ConstantsInitializer;
import com.harvey.se.util.RedisIdWorker;
import com.harvey.se.util.ServerConstants;
import com.harvey.se.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-11
 */
@Slf4j
@RestController
@Api(tags = "和LLM聊天")
@RequestMapping("/robot")
@EnableConfigurationProperties(ConstantsProperties.class)
public class RobotChatController {
    @Resource
    private RobotChatService robotChatService;

    @Resource
    private ChatMessageService chatMessageService;

    @Resource
    private ConstantsInitializer constantsInitializer;

    @Resource
    private RedisIdWorker redisIdWorker;

    @PostMapping(value = "/chat/qwen")
    @ApiOperation(
            "用户问问题, Qwen进行回答, 回答使用流式回答. 在回答期间, 用户再问问题, 问题将被忽略. 请客户端阻止用户问问题. 有积分拿, 每日五次, 每次五分 ")
    public Result<Long> streamChatQwen(@RequestBody String message) throws InterruptedException {
        return new Result<>(robotChatService.chat(UserHolder.getUser(), message, 0));
    }

    @PostMapping(value = "/chat/deepseek")
    @ApiOperation(
            "用户问问题, Deepseek进行回答, 回答使用流式回答. 在回答期间, 用户再问问题, 问题将被忽略. 请客户端阻止用户问问题. 有积分拿, 每日五次, 每次五分 ")
    public Result<Long> streamChatDeepseek(@RequestBody String message) throws InterruptedException {
        return new Result<>(robotChatService.chat(UserHolder.getUser(), message, 1));
    }

    @ApiOperation("获取文本片段")
    @DeleteMapping(value = {"/pieces/{chat-id}/{limit}", /*"/pieces/{chat-id}"*/})
    public Result<List<ChatTextPiece>> pullPieces(
            @PathVariable("chat-id") @ApiParam(value = "聊天ID", required = true) Long chatId,
            @PathVariable(value = "limit", required = true) @ApiParam(value = "取几个碎片, 上限20", required = true)
            Integer limit) {
        return new Result<>(robotChatService.pullPieces(chatId, limit));
    }

    @GetMapping(value = {"/history/me/{time-from}/{time-to}/{limit}/{page}"/*, "/history/me/{time-from}/{time-to}/{limit}",
                    "/history/me/{time-from}/{time-to}", "/history/me/{time-from}", "/history/me",*/})
    @ApiOperation("查询一定时间内的用户聊天记录")
    @ApiResponse(code = 200, message = "按照时间排序, 返回的时间顺序和参数的from-to一致")
    public Result<List<ChatMessageDto>> queryByTimeRange(
            @PathVariable(value = "time-from", required = true)
            @ApiParam(value = "日期查询的起点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING,
                    required = true) String timeFrom,
            @PathVariable(value = "time-to", required = true)
            @ApiParam(value = "日期查询的终点(包含)", example = ServerConstants.DATE_TIME_FORMAT_STRING,
                    required = true) String timeTo,
            @PathVariable(value = "limit", required = true)
            @ApiParam(value = "页长", defaultValue = ServerConstants.DEFAULT_PAGE_SIZE, required = true) Integer limit,
            @PathVariable(value = "page", required = true)
            @ApiParam(value = "页号", defaultValue = "1", required = true) Integer page) {
        // 依据请求发送的时间查询
        DateRange dateRange;
        try {
            dateRange = ConstantsInitializer.initDateRange(timeFrom, timeTo);
        } catch (ParseException e) {
            throw new BadRequestException("错误的日期格式", e);
        }
        return new Result<>(chatMessageService.queryByUser(
                UserHolder.currentUserId(),
                dateRange,
                constantsInitializer.initPage(page, limit)
        ));
    }
}
