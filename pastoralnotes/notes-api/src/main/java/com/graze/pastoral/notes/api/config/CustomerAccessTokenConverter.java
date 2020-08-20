package com.graze.pastoral.notes.api.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 自定义jwt解析
 */
public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter {

    public CustomerAccessTokenConverter() {
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }

    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            return new UsernamePasswordAuthenticationToken(map, "N/A", this.getAuthorities(map));
        }

        private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
            if (!map.containsKey(AUTHORITIES)) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                        .arrayToCommaDelimitedString(new String[]{}));
            }
            Object authorities = map.get(AUTHORITIES);
            if (authorities instanceof String) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
            }
            if (authorities instanceof Collection) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(
                        StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
            }
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }
    }

}
