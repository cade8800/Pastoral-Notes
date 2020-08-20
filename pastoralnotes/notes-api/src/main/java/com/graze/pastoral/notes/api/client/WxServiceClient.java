package com.graze.pastoral.notes.api.client;

import com.graze.pastoral.notes.domain.dto.user.MiniProgramLoginMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/6/12 17:54
 */
@FeignClient(name = "weixin", url = "https://api.weixin.qq.com")
public interface WxServiceClient {
    @GetMapping(value = "/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code")
    MiniProgramLoginMsg authorizationCode(@PathVariable String appid, @PathVariable String secret, @PathVariable String js_code);
}
