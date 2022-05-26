--  H2 数据库专用 MySQL 可以通过JPA 自动创建

drop schema if exists `id_server`;
create schema `id_server`;
use `id_server`;

create table `authorization`
(
    `id`                          varchar(100)                            not null,
    registered_client_id          varchar(100)                            not null,
    principal_name                varchar(200)                            not null,
    authorization_grant_type      varchar(100)                            not null,
    attributes                    text                                    null,
    state                         varchar(500)                            null,
    authorization_code_value      text                                    null,
    authorization_code_issued_at  timestamp default CURRENT_TIMESTAMP     null,
    authorization_code_expires_at timestamp default '0000-00-00 00:00:00' null,
    authorization_code_metadata   text                                    null,
    access_token_value            text                                    null,
    access_token_issued_at        timestamp default CURRENT_TIMESTAMP     null,
    access_token_expires_at       timestamp default '0000-00-00 00:00:00' null,
    access_token_metadata         text                                    null,
    access_token_type             varchar(100)                            null,
    access_token_scopes           varchar(1000)                           null,
    oidc_id_token_value           text                                    null,
    oidc_id_token_issued_at       timestamp default CURRENT_TIMESTAMP     null,
    oidc_id_token_expires_at      timestamp default CURRENT_TIMESTAMP     null,
    oidc_id_token_metadata        text                                    null,
    refresh_token_value           text                                    null,
    refresh_token_issued_at       timestamp default CURRENT_TIMESTAMP     null,
    refresh_token_expires_at      timestamp default CURRENT_TIMESTAMP     null,
    refresh_token_metadata        text                                    null,
    oidc_id_token_claims          varchar(2000)                           null,
    primary key (`id`)
);

create table authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

create table client_auth_method
(
    client_id                    varchar(255) not null comment 'oauth2客户端id',
    client_authentication_method varchar(255) not null comment '客户端认证方式',
    primary key (client_id, client_authentication_method)
)
    comment 'oauth2客户端认证方式表';

create table menu
(
    `id`      varchar(255) not null,
    parent_id varchar(255) not null,
    title     varchar(255) not null,
    type      varchar(255) null comment '是否可跳转，0 不可跳转  1 可跳转',
    open_type varchar(255) null,
    icon      varchar(255) null,
    href      varchar(255) null,
    primary key (`id`)
);

create table oauth2_client_settings
(
    client_id                     varchar(255)         not null comment 'oauth2客户端id',
    require_proof_key             tinyint(1) default 0 null comment '客户端是否需要证明密钥',
    require_authorization_consent tinyint(1) default 0 null comment '客户端是否需要授权确认页面',
    jwk_set_url                   varchar(255)         null comment 'jwkSet url',
    signing_algorithm             varchar(255)         null comment '支持的签名算法',
    primary key (client_id)
)
    comment 'oauth2客户端配置';

create table oauth2_client
(
    `id`                     varchar(100)                        not null comment 'ID',
    client_id                varchar(100)                        not null comment 'oauth2客户端id',
    client_id_issued_at      timestamp default CURRENT_TIMESTAMP not null comment '客户端创建时间',
    client_secret            varchar(200)                        not null comment '客户端密码',
    client_secret_expires_at timestamp                           null comment '客户端密码过期时间',
    client_name              varchar(200)                        not null comment '客户端名称',
    primary key (`id`),
    constraint UK_drwlno5wbex09l0acnnwecp7r
        unique (client_id)
)
    comment 'oauth2客户端基础信息表';

create table oauth2_grant_type
(
    client_id       varchar(255) not null comment 'oauth2客户端id',
    grant_type_name varchar(255) not null comment '客户端授权方式',
    primary key (client_id, grant_type_name)
)
    comment 'oauth2客户端授权方式表';

create table oauth2_scope
(
    client_id   varchar(255) not null comment 'oauth2客户端id',
    scope       varchar(255) not null comment '客户端允许的scope 来自role表',
    description varchar(255) null,
    primary key (client_id, scope)
)
    comment 'oauth2客户端授权范围';

create table oauth2_token_settings
(
    client_id                    varchar(255)         not null comment 'oauth2客户端id',
    access_token_time_to_live    bigint               null comment 'access_token 有效时间',
    token_format                 varchar(255)         null comment 'token 格式  jwt、opaque',
    reuse_refresh_tokens         tinyint(1) default 1 null comment '是否重用 refresh_token',
    refresh_token_time_to_live   bigint               null comment 'refresh_token 有效时间',
    id_token_signature_algorithm varchar(255)         null comment 'oidc id_token 签名算法',
    primary key (client_id)
)
    comment 'oauth2客户端的token配置项';

create table permission
(
    permission_id   varchar(255) not null,
    create_id       varchar(255) null,
    create_time     datetime(6)  null,
    `description`   varchar(255) null,
    enabled         bit          null,
    parent_id       varchar(255) null,
    permission_code varchar(255) null,
    sortable        int          null,
    title           varchar(255) null,
    update_id       varchar(255) null,
    update_time     datetime(6)  null,
    primary key (permission_id),
    constraint UKpubb8bn5j1jwu50vnykvbap9w
        unique (permission_code)
);

create table redirect_uri
(
    client_id    varchar(255) not null comment 'oauth2客户端id',
    redirect_uri varchar(255) not null comment '客户端允许重定向的uri',
    primary key (client_id, redirect_uri)
)
    comment 'oauth2客户端重定向uri表';

create table `role`
(
    role_id       varchar(64)          not null comment '角色ID',
    role_name     varchar(32)          not null comment '角色名称',
    role_content  varchar(500)         not null comment '展示名称',
    `description` varchar(255)         null comment '描述',
    create_time   datetime             null comment '创建时间',
    create_id     varchar(64)          null comment '创建人ID',
    update_time   datetime             null comment '修改时间',
    update_id     varchar(64)          null comment '修改人ID',
    enabled       tinyint(1) default 1 null comment '删除状态（1-正常，0-删除）',
    primary key (role_id),
    constraint UKiubw515ff0ugtm28p8g3myt0h
        unique (role_name)
)
    comment '角色权限表';

create table role_permission
(
    role_id       varchar(255) not null,
    permission_id varchar(255) not null,
    primary key (role_id, permission_id),
    constraint FKa6jx8n8xkesmjmv6jqug6bg68
        foreign key (role_id) references role (role_id),
    constraint FKf8yllw1ecvwqy3ehyxawqa1qp
        foreign key (permission_id) references permission (permission_id)
);

create table system_settings
(
    `id`          varchar(255) not null,
    system_name varchar(255) null,
    copyright   varchar(255) null,
    primary key (`id`)
);

create table user_info
(
    user_id      varchar(64)           not null comment '用户ID',
    username     varchar(32)           not null comment '用户名称',
    `password`     varchar(255)          not null comment '密码',
    nick_name    varchar(32)           null comment '昵称',
    real_name    varchar(32)           null comment '真实姓名',
    phone_number varchar(16)           null comment '手机号',
    avatar_url   varchar(255)          null comment '头像',
    email        varchar(64)           null comment '邮箱',
    gender       tinyint(1) default -1 null comment '性别   -1 未知 0 女性  1 男性 ',
    create_time  datetime              null comment '创建时间',
    create_id    varchar(64)           null comment '创建人ID',
    update_time  datetime              null comment '修改时间',
    update_id    varchar(64)           null comment '修改人ID',
    enabled      tinyint(1) default 1  null comment '删除状态（1-正常，0-删除）',
    secret       varchar(255)          null,
    primary key (user_id),
    constraint user_info_phone_number_uindex
        unique (phone_number),
    constraint username
        unique (username)
)
    comment '用户表';

create table user_role
(
    user_id varchar(255) not null,
    role_id varchar(255) not null,
    primary key (user_id, role_id),
    constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id) references role (role_id),
    constraint FKm90yi1ak9h9tbct25k3km0hia
        foreign key (user_id) references user_info (user_id)
);

