package com.graze.pastoral.notes.api.client;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/13 12:09
 */
public class UaaErrorConfiguration {

    private static Logger logger = LoggerFactory.getLogger(UaaErrorConfiguration.class);

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UaaErrorDecoder();
    }

    public class UaaErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;

//        String authType = response.request().requestTemplate().queries().get("auth_type");
            try {
                String json = Util.toString(response.body().asReader(Charset.forName("GBK")));
                logger.error("登录错误:" + json);
                exception = new RuntimeException(json);//包装原始错误
            } catch (IOException ex) {
                logger.error("[Feign包装原始错误解析response.body()出错]" + ex.getMessage());
            }

            return exception;
        }
    }

}
