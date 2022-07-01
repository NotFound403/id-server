/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.felord.idserver.authentication.providers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.Arrays;
import java.util.Objects;

/**
 * 授权请求参数的请求参数封装工具类,扩展了{@link OAuth2AuthorizationCodeGrantRequestEntityConverter}
 *
 * @author felord.cn
 * @see OAuth2AuthorizationCodeGrantRequestEntityConverter
 * @see Converter
 * @see OAuth2AuthorizationCodeGrantRequest
 * @see RequestEntity
 * @since 5.1
 */
public class OAuth2ProviderAuthorizationCodeGrantRequestEntityConverter
        implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {
    private final Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();

    /**
     * Returns the {@link RequestEntity} used for the Access Token Request.
     *
     * @param authorizationCodeGrantRequest the authorization code grant request
     * @return the {@link RequestEntity} used for the Access Token Request
     */
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {

        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();
        return Arrays.stream(ClientProviders.values())
                .filter(clientProvider ->
                        Objects.equals(clientProvider.registrationId(), registrationId))
                .findAny()
                .map(ClientProviders::converter)
                .orElse(defaultConverter)
                .convert(authorizationCodeGrantRequest);
    }
}
