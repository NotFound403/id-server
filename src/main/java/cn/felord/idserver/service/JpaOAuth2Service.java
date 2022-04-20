package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.repository.OAuth2ScopeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;

@Service
public class JpaOAuth2Service implements OAuth2Service {
    private final OAuth2ScopeRepository oAuth2ScopeRepository;

    public JpaOAuth2Service(OAuth2ScopeRepository oAuth2ScopeRepository) {
        this.oAuth2ScopeRepository = oAuth2ScopeRepository;
    }

    @Override
    public Set<OAuth2Scope> findByNames(String clientId, Collection<String> names) {
        Assert.notEmpty(names, "names is not empty");
        return oAuth2ScopeRepository.findByClientIdAndScope(clientId, names);
    }
}
