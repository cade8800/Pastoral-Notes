package com.graze.pastoral.notes.api.service;

import com.graze.pastoral.notes.domain.type.RedisKeyPrefix;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/17 11:43
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * 生成随机验证码并保存进redis
     *
     * @param mobile
     * @return
     */
    public String setSmsCaptcha(String mobile) {
        ValueOperations<String, String> strRedis = stringRedisTemplate.opsForValue();
        String targetCaptcha = strRedis.get(RedisKeyPrefix.UserMobileCaptcha + mobile);
        if (!StringUtils.isBlank(targetCaptcha)) {
            Long expireSecond = strRedis.getOperations().getExpire(RedisKeyPrefix.UserMobileCaptcha + mobile, TimeUnit.SECONDS);
            if (expireSecond >= 240) {
                throw new RuntimeException("重复发送，请1分钟后再试");
            }
        }
        targetCaptcha = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println(targetCaptcha);
        strRedis.set(RedisKeyPrefix.UserMobileCaptcha + mobile, targetCaptcha, 300, TimeUnit.SECONDS);

        return targetCaptcha;
    }

    /**
     * 检查验证码是否正确 正确后清空redis
     *
     * @param mobile
     * @param captcha
     */
    public void checkSmsCaptcha(String mobile, String captcha) {
        ValueOperations<String, String> strRedis = stringRedisTemplate.opsForValue();
        String targetCaptcha = strRedis.get(RedisKeyPrefix.UserMobileCaptcha + mobile);

        if (StringUtils.isBlank(targetCaptcha) || !captcha.equals(targetCaptcha)) {
            throw new RuntimeException("短信验证码错误");
        }

        // 重置redis
        strRedis.set(RedisKeyPrefix.UserMobileCaptcha + mobile, "", 1, TimeUnit.SECONDS);
    }

    public String setEmailCaptcha(String mail) {
        ValueOperations<String, String> strRedis = stringRedisTemplate.opsForValue();
        String targetCaptcha = strRedis.get(RedisKeyPrefix.UserEmailCaptcha + mail);
        if (!StringUtils.isBlank(targetCaptcha)) {
            Long expireSecond = strRedis.getOperations().getExpire(RedisKeyPrefix.UserEmailCaptcha + mail, TimeUnit.SECONDS);
            if (expireSecond >= 240) {
                throw new RuntimeException("重复发送，请1分钟后再试");
            }
        }
        targetCaptcha = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println(targetCaptcha);
        strRedis.set(RedisKeyPrefix.UserEmailCaptcha + mail, targetCaptcha, 300, TimeUnit.SECONDS);

        return targetCaptcha;
    }

    public void checkEmailCaptcha(String mail, String mailCaptcha) {
        ValueOperations<String, String> strRedis = stringRedisTemplate.opsForValue();
        String targetCaptcha = strRedis.get(RedisKeyPrefix.UserEmailCaptcha + mail);

        if (StringUtils.isBlank(targetCaptcha) || !mailCaptcha.equals(targetCaptcha)) {
            throw new RuntimeException("邮箱验证码错误");
        }

        // 重置redis
        strRedis.set(RedisKeyPrefix.UserEmailCaptcha + mail, "", 1, TimeUnit.SECONDS);
    }
}
