package com.graze.pastoral.notes.api.client;


import com.graze.pastoral.notes.domain.dto.auth.JWTDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "notes-uaa", configuration = UaaErrorConfiguration.class, fallbackFactory = UaaServiceFallbackFactory.class)
//, fallback = AuthServiceHystrix.class
public interface UaaServiceClient {

    @PostMapping(value = "/oauth/token")
    JWTDto getToken(@RequestHeader(value = "Authorization") String authorization,
                    @RequestParam("grant_type") String grantType,
                    @RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("auth_type") String authType);

    @PostMapping(value = "/oauth/token")
    JWTDto getToken(@RequestHeader(value = "Authorization") String authorization,
                    @RequestParam("grant_type") String grantType,
                    @RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("auth_type") String authType,
                    @RequestParam("session_key") String sessionKey,
                    @RequestParam("wx_app_id") String wxAppId,
                    @RequestParam("union_id") String unionId);
}
