CREATE TABLE system_settings
(
    id          VARCHAR(255) NOT NULL,
    system_name VARCHAR(255) NULL,
    copyright   VARCHAR(255) NULL,
    CONSTRAINT pk_systemsettings PRIMARY KEY (id)
);

create table authorization
(
    id                            varchar(100)                        not null
        primary key,
    registered_client_id          varchar(100)                        not null,
    principal_name                varchar(200)                        not null,
    authorization_grant_type      varchar(100)                        not null,
    attributes                    blob                                null,
    state                         varchar(500)                        null,
    authorization_code_value      blob                                null,
    authorization_code_issued_at  timestamp default CURRENT_TIMESTAMP not null,
    authorization_code_expires_at timestamp                           not null,
    authorization_code_metadata   blob                                null,
    access_token_value            blob                                null,
    access_token_issued_at        timestamp default CURRENT_TIMESTAMP not null,
    access_token_expires_at       timestamp                           not null,
    access_token_metadata         blob                                null,
    access_token_type             varchar(100)                        null,
    access_token_scopes           varchar(1000)                       null,
    oidc_id_token_value           blob                                null,
    oidc_id_token_issued_at       timestamp default CURRENT_TIMESTAMP not null,
    oidc_id_token_expires_at      timestamp default CURRENT_TIMESTAMP not null,
    oidc_id_token_metadata        blob                                null,
    refresh_token_value           blob                                null,
    refresh_token_issued_at       timestamp default CURRENT_TIMESTAMP not null,
    refresh_token_expires_at      timestamp default CURRENT_TIMESTAMP not null,
    refresh_token_metadata        blob                                null
);

create table authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

create table oauth2_client
(
    id                       varchar(100)                        not null
        primary key comment 'ID',
    client_id                varchar(100) unique                 not null comment 'oauth2客户端id',
    client_id_issued_at      timestamp default CURRENT_TIMESTAMP not null comment '客户端创建时间',
    client_secret            varchar(200)                        not null comment '客户端密码',
    client_secret_expires_at timestamp                           null comment '客户端密码过期时间',
    client_name              varchar(200)                        not null comment '客户端名称'
) comment 'oauth2客户端基础信息表';

CREATE TABLE client_auth_method
(
    client_id                    VARCHAR(255) NOT NULL comment 'oauth2客户端id',
    client_authentication_method VARCHAR(255) NOT NULL comment '客户端认证方式',
    PRIMARY KEY (client_id, client_authentication_method)
) comment 'oauth2客户端认证方式表';

CREATE TABLE oauth2_grant_type
(
    client_id       VARCHAR(255) NOT NULL comment 'oauth2客户端id',
    grant_type_name VARCHAR(255) NOT NULL comment '客户端授权方式',
    PRIMARY KEY (client_id, grant_type_name)
) comment 'oauth2客户端授权方式表';

CREATE TABLE redirect_uri
(
    client_id    VARCHAR(255) NOT NULL comment 'oauth2客户端id',
    redirect_uri VARCHAR(255) NOT NULL comment '客户端允许重定向的uri',
    PRIMARY KEY (client_id, redirect_uri)
) comment 'oauth2客户端重定向uri表';

CREATE TABLE oauth2_scope
(
    client_id VARCHAR(255) NOT NULL comment 'oauth2客户端id',
    scope     VARCHAR(255) NOT NULL comment '客户端允许的scope 来自role表',
    PRIMARY KEY (client_id, scope)
) comment 'oauth2客户端授权范围';


CREATE TABLE oauth2_client_settings
(
    client_id                     VARCHAR(255)         NOT NULL comment 'oauth2客户端id',
    require_proof_key             BIT(1) default false NULL comment '客户端是否需要证明密钥',
    require_authorization_consent BIT(1) default false NULL comment '客户端是否需要授权确认页面',
    jwk_set_url                   VARCHAR(255)         NULL comment 'jwkSet url',
    signing_algorithm             VARCHAR(255)         NULL comment '支持的签名算法',
    PRIMARY KEY (client_id)
) comment 'oauth2客户端配置项';

CREATE TABLE oauth2_token_settings
(
    client_id                    VARCHAR(255)        NOT NULL comment 'oauth2客户端id',
    access_token_time_to_live    BIGINT              NULL comment 'access_token 有效时间',
    token_format                 VARCHAR(255)        NULL comment 'token 格式  jwt、opaque',
    reuse_refresh_tokens         BIT(1) default true NULL comment '是否重用 refresh_token',
    refresh_token_time_to_live   BIGINT              NULL comment 'refresh_token 有效时间',
    id_token_signature_algorithm VARCHAR(255)        NULL comment 'oidc id_token 签名算法',
    PRIMARY KEY (client_id)
)comment 'oauth2客户端的token配置项';

create table menu
(
    id        varchar(255) not null
        primary key,
    parent_id varchar(255) null,
    title     varchar(255) null,
    type      varchar(255) null,
    open_type varchar(255) null,
    icon      varchar(255) null,
    href      varchar(255) null
);

create table role
(
    role_id      varchar(64)          not null comment '角色ID'
        primary key,
    client_id    varchar(64)          not null comment '所属客户端',
    role_name    varchar(32)          not null comment '角色名称',
    role_content varchar(500)         not null comment '备注',
    create_time  datetime             null comment '创建时间',
    create_id    varchar(64)          null comment '创建人ID',
    update_time  datetime             null comment '修改时间',
    update_id    varchar(64)          null comment '修改人ID',
    enabled      tinyint(1) default 1 null comment '删除状态（1-正常，0-删除）',
    constraint role_client_udx
        unique (client_id, role_name)
)
    comment '角色权限表';


create table user_info
(
    user_id      varchar(64)           not null comment '用户ID'
        primary key,
    username     varchar(32)           not null comment '用户名称',
    secret       varchar(256)          null comment '密码',
    nick_name    varchar(32)           null comment '昵称',
    real_name    varchar(32)           null comment '真实姓名',
    phone_number varchar(16)           not null comment '手机号',
    avatar_url   varchar(255)          null comment '头像',
    email        varchar(64)           null comment '邮箱',
    gender       tinyint(1) default -1 null comment '性别   -1 未知 0 女性  1 男性 ',
    create_time  datetime              null comment '创建时间',
    create_id    varchar(64)           null comment '创建人ID',
    update_time  datetime              null comment '修改时间',
    update_id    varchar(64)           null comment '修改人ID',
    enabled      tinyint(1) default 1  null comment '删除状态（1-正常，0-删除）',
    constraint username
        unique (username)
)
    comment '用户表';

