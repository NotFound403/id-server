## OIDC 1.0 登录

## 使用
1. 先启动[Id Server](https://github.com/NotFound403/id-server)。
2. 然后启动本项目。
3. 浏览器输入 [http://localhost:8083/oauth2/authorization/felord](http://localhost:8083/oauth2/authorization/felord)。
4. 重定向到[Id Server](https://github.com/NotFound403/id-server)的授权登录页。
5. 输入测试用户名密码`user/user`。
6. 跳转到`/`输出认证信息。
  
### 附认证信息

```json
{
    "authentication": {
        "authorities": [
            {
                "authority": "ROLE_USER",
                "attributes": {
                    "sub": "user",
                    "aud": [
                        "e2fa7e64-249b-46f0-ae1d-797610e88615"
                    ],
                    "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                    "iss": "http://localhost:9000",
                    "exp": "2022-05-14T09:32:01Z",
                    "iat": "2022-05-14T09:02:01Z",
                    "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
                },
                "idToken": {
                    "tokenValue": "eyJ4NXQjUzI1NiI6IlN4cXFkV1l4VDdCWnJkSC11VnBnQUhmWDJxMzRxUHl4eDRvblg2bXYtcUkiLCJraWQiOiJqb3NlIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ1c2VyIiwiYXVkIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiYXpwIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2NTI1MjA3MjEsImlhdCI6MTY1MjUxODkyMSwibm9uY2UiOiJ6OWNmdVUwaU9HRk9DSVZDZktWQWVqZjVmazRLd3plWnNLVmtGdGlFbXljIn0.OCU2CgGcRzBZfPvSh9xJE89ppreAnWrHWhBegmta8qwpaiz8EI0EPJq1xL4gNt_q7wZqtMDZkp5SKACjRdKcskLjL6lxT7krvGIrGGn0fLnrScVYcvWd2hLYPlXkL0RQKjo2Ne5z9uh7Cwyaox211ssLDRsV60503abMg0FYLbrH44yUPKtpkwP6Yn8RCbF0eY8ipzLStcUaEQgZyEgzvP0et48VQ-Ly8wueRAD9DM21XoFBPZb0jDW0fQFQ2naUDj6zm8ay_QgTX_yzLYMXhT69dJgGRYw6-crS9MuFK7oIhm3ADNYW-FXROXvvog_Y89wkpPS4yGjWdYPxFHN3Ng",
                    "issuedAt": "2022-05-14T09:02:01Z",
                    "expiresAt": "2022-05-14T09:32:01Z",
                    "claims": {
                        "sub": "user",
                        "aud": [
                            "e2fa7e64-249b-46f0-ae1d-797610e88615"
                        ],
                        "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                        "iss": "http://localhost:9000",
                        "exp": "2022-05-14T09:32:01Z",
                        "iat": "2022-05-14T09:02:01Z",
                        "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
                    },
                    "subject": "user",
                    "issuer": "http://localhost:9000",
                    "audience": [
                        "e2fa7e64-249b-46f0-ae1d-797610e88615"
                    ],
                    "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc",
                    "authenticatedAt": null,
                    "authenticationMethods": null,
                    "authenticationContextClass": null,
                    "accessTokenHash": null,
                    "authorizationCodeHash": null,
                    "authorizedParty": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                    "address": {
                        "formatted": null,
                        "streetAddress": null,
                        "locality": null,
                        "region": null,
                        "postalCode": null,
                        "country": null
                    },
                    "locale": null,
                    "zoneInfo": null,
                    "profile": null,
                    "fullName": null,
                    "familyName": null,
                    "picture": null,
                    "email": null,
                    "gender": null,
                    "givenName": null,
                    "phoneNumber": null,
                    "website": null,
                    "birthdate": null,
                    "nickName": null,
                    "middleName": null,
                    "emailVerified": null,
                    "updatedAt": null,
                    "phoneNumberVerified": null,
                    "preferredUsername": null
                },
                "userInfo": null
            },
            {
                "authority": "SCOPE_message.read"
            },
            {
                "authority": "SCOPE_message.write"
            },
            {
                "authority": "SCOPE_openid"
            }
        ],
        "details": {
            "remoteAddress": "127.0.0.1",
            "sessionId": "B09B635C068C92498351C4AF51AF13F0"
        },
        "authenticated": true,
        "principal": {
            "authorities": [
                {
                    "authority": "ROLE_USER",
                    "attributes": {
                        "sub": "user",
                        "aud": [
                            "e2fa7e64-249b-46f0-ae1d-797610e88615"
                        ],
                        "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                        "iss": "http://localhost:9000",
                        "exp": "2022-05-14T09:32:01Z",
                        "iat": "2022-05-14T09:02:01Z",
                        "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
                    },
                    "idToken": {
                        "tokenValue": "eyJ4NXQjUzI1NiI6IlN4cXFkV1l4VDdCWnJkSC11VnBnQUhmWDJxMzRxUHl4eDRvblg2bXYtcUkiLCJraWQiOiJqb3NlIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ1c2VyIiwiYXVkIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiYXpwIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2NTI1MjA3MjEsImlhdCI6MTY1MjUxODkyMSwibm9uY2UiOiJ6OWNmdVUwaU9HRk9DSVZDZktWQWVqZjVmazRLd3plWnNLVmtGdGlFbXljIn0.OCU2CgGcRzBZfPvSh9xJE89ppreAnWrHWhBegmta8qwpaiz8EI0EPJq1xL4gNt_q7wZqtMDZkp5SKACjRdKcskLjL6lxT7krvGIrGGn0fLnrScVYcvWd2hLYPlXkL0RQKjo2Ne5z9uh7Cwyaox211ssLDRsV60503abMg0FYLbrH44yUPKtpkwP6Yn8RCbF0eY8ipzLStcUaEQgZyEgzvP0et48VQ-Ly8wueRAD9DM21XoFBPZb0jDW0fQFQ2naUDj6zm8ay_QgTX_yzLYMXhT69dJgGRYw6-crS9MuFK7oIhm3ADNYW-FXROXvvog_Y89wkpPS4yGjWdYPxFHN3Ng",
                        "issuedAt": "2022-05-14T09:02:01Z",
                        "expiresAt": "2022-05-14T09:32:01Z",
                        "claims": {
                            "sub": "user",
                            "aud": [
                                "e2fa7e64-249b-46f0-ae1d-797610e88615"
                            ],
                            "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                            "iss": "http://localhost:9000",
                            "exp": "2022-05-14T09:32:01Z",
                            "iat": "2022-05-14T09:02:01Z",
                            "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
                        },
                        "subject": "user",
                        "issuer": "http://localhost:9000",
                        "audience": [
                            "e2fa7e64-249b-46f0-ae1d-797610e88615"
                        ],
                        "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc",
                        "authenticatedAt": null,
                        "authenticationMethods": null,
                        "authenticationContextClass": null,
                        "accessTokenHash": null,
                        "authorizationCodeHash": null,
                        "authorizedParty": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                        "address": {
                            "formatted": null,
                            "streetAddress": null,
                            "locality": null,
                            "region": null,
                            "postalCode": null,
                            "country": null
                        },
                        "locale": null,
                        "zoneInfo": null,
                        "profile": null,
                        "fullName": null,
                        "familyName": null,
                        "picture": null,
                        "email": null,
                        "gender": null,
                        "givenName": null,
                        "phoneNumber": null,
                        "website": null,
                        "birthdate": null,
                        "nickName": null,
                        "middleName": null,
                        "emailVerified": null,
                        "updatedAt": null,
                        "phoneNumberVerified": null,
                        "preferredUsername": null
                    },
                    "userInfo": null
                },
                {
                    "authority": "SCOPE_message.read"
                },
                {
                    "authority": "SCOPE_message.write"
                },
                {
                    "authority": "SCOPE_openid"
                }
            ],
            "attributes": {
                "sub": "user",
                "aud": [
                    "e2fa7e64-249b-46f0-ae1d-797610e88615"
                ],
                "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                "iss": "http://localhost:9000",
                "exp": "2022-05-14T09:32:01Z",
                "iat": "2022-05-14T09:02:01Z",
                "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
            },
            "idToken": {
                "tokenValue": "eyJ4NXQjUzI1NiI6IlN4cXFkV1l4VDdCWnJkSC11VnBnQUhmWDJxMzRxUHl4eDRvblg2bXYtcUkiLCJraWQiOiJqb3NlIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ1c2VyIiwiYXVkIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiYXpwIjoiZTJmYTdlNjQtMjQ5Yi00NmYwLWFlMWQtNzk3NjEwZTg4NjE1IiwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2NTI1MjA3MjEsImlhdCI6MTY1MjUxODkyMSwibm9uY2UiOiJ6OWNmdVUwaU9HRk9DSVZDZktWQWVqZjVmazRLd3plWnNLVmtGdGlFbXljIn0.OCU2CgGcRzBZfPvSh9xJE89ppreAnWrHWhBegmta8qwpaiz8EI0EPJq1xL4gNt_q7wZqtMDZkp5SKACjRdKcskLjL6lxT7krvGIrGGn0fLnrScVYcvWd2hLYPlXkL0RQKjo2Ne5z9uh7Cwyaox211ssLDRsV60503abMg0FYLbrH44yUPKtpkwP6Yn8RCbF0eY8ipzLStcUaEQgZyEgzvP0et48VQ-Ly8wueRAD9DM21XoFBPZb0jDW0fQFQ2naUDj6zm8ay_QgTX_yzLYMXhT69dJgGRYw6-crS9MuFK7oIhm3ADNYW-FXROXvvog_Y89wkpPS4yGjWdYPxFHN3Ng",
                "issuedAt": "2022-05-14T09:02:01Z",
                "expiresAt": "2022-05-14T09:32:01Z",
                "claims": {
                    "sub": "user",
                    "aud": [
                        "e2fa7e64-249b-46f0-ae1d-797610e88615"
                    ],
                    "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                    "iss": "http://localhost:9000",
                    "exp": "2022-05-14T09:32:01Z",
                    "iat": "2022-05-14T09:02:01Z",
                    "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
                },
                "subject": "user",
                "issuer": "http://localhost:9000",
                "audience": [
                    "e2fa7e64-249b-46f0-ae1d-797610e88615"
                ],
                "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc",
                "authenticatedAt": null,
                "authenticationMethods": null,
                "authenticationContextClass": null,
                "accessTokenHash": null,
                "authorizationCodeHash": null,
                "authorizedParty": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                "address": {
                    "formatted": null,
                    "streetAddress": null,
                    "locality": null,
                    "region": null,
                    "postalCode": null,
                    "country": null
                },
                "locale": null,
                "zoneInfo": null,
                "profile": null,
                "fullName": null,
                "familyName": null,
                "picture": null,
                "email": null,
                "gender": null,
                "givenName": null,
                "phoneNumber": null,
                "website": null,
                "birthdate": null,
                "nickName": null,
                "middleName": null,
                "emailVerified": null,
                "updatedAt": null,
                "phoneNumberVerified": null,
                "preferredUsername": null
            },
            "userInfo": null,
            "claims": {
                "sub": "user",
                "aud": [
                    "e2fa7e64-249b-46f0-ae1d-797610e88615"
                ],
                "azp": "e2fa7e64-249b-46f0-ae1d-797610e88615",
                "iss": "http://localhost:9000",
                "exp": "2022-05-14T09:32:01Z",
                "iat": "2022-05-14T09:02:01Z",
                "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc"
            },
            "name": "user",
            "subject": "user",
            "issuer": "http://localhost:9000",
            "expiresAt": "2022-05-14T09:32:01Z",
            "audience": [
                "e2fa7e64-249b-46f0-ae1d-797610e88615"
            ],
            "issuedAt": "2022-05-14T09:02:01Z",
            "nonce": "z9cfuU0iOGFOCIVCfKVAejf5fk4KwzeZsKVkFtiEmyc",
            "authenticatedAt": null,
            "authenticationMethods": null,
            "authenticationContextClass": null,
            "accessTokenHash": null,
            "authorizationCodeHash": null,
            "authorizedParty": "e2fa7e64-249b-46f0-ae1d-797610e88615",
            "address": {
                "formatted": null,
                "streetAddress": null,
                "locality": null,
                "region": null,
                "postalCode": null,
                "country": null
            },
            "locale": null,
            "zoneInfo": null,
            "profile": null,
            "fullName": null,
            "familyName": null,
            "picture": null,
            "email": null,
            "gender": null,
            "givenName": null,
            "phoneNumber": null,
            "website": null,
            "birthdate": null,
            "nickName": null,
            "middleName": null,
            "emailVerified": null,
            "updatedAt": null,
            "phoneNumberVerified": null,
            "preferredUsername": null
        },
        "authorizedClientRegistrationId": "felord",
        "credentials": "",
        "name": "user"
    }
}
```