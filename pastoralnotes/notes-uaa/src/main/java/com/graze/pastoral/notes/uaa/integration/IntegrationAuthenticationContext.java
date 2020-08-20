package com.graze.pastoral.notes.uaa.integration;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/12 15:42
 */
public class IntegrationAuthenticationContext {

    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication) {
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
