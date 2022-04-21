package cn.felord.idserver.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * The type Base controller.
 *
 * @author n1
 * @since 2021 /4/12 15:00
 */
public abstract class BaseController {


    /**
     * Current user object.
     *
     * @return the object
     */
    public Authentication currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Current user roles collection.
     *
     * @return the collection
     */
    public Collection<? extends GrantedAuthority> currentUserRoles(){
        Collection<? extends GrantedAuthority> authorities = currentUser().getAuthorities();
        return CollectionUtils.isEmpty(authorities)? Collections.emptyList():authorities;
    }

    /**
     * 获取当前用户名. aud
     *
     * @return the string
     */
    public String currentUserName() {
        return currentUser().getName();
    }
}
