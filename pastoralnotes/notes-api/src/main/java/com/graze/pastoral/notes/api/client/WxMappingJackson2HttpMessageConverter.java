package com.graze.pastoral.notes.api.client;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/6/12 19:58
 */
public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public WxMappingJackson2HttpMessageConverter() {

        List<MediaType> mediaTypes = new ArrayList<>();

        mediaTypes.add(MediaType.TEXT_PLAIN);

        mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持

        setSupportedMediaTypes(mediaTypes);// tag6

    }

}