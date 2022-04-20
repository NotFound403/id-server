-- Spring Authorization Server Mysql DDL
create database id_server_database;

CREATE TABLE client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT CURRENT_TIMESTAMP,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE authorization_consent
(
    registered_client_id varchar(100)  NOT NULL,
    principal_name       varchar(200)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);
CREATE TABLE authorization
(
    id                            varchar(100) NOT NULL,
    registered_client_id          varchar(100) NOT NULL,
    principal_name                varchar(200) NOT NULL,
    authorization_grant_type      varchar(100) NOT NULL,
    attributes                    blob          DEFAULT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      blob          DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT CURRENT_TIMESTAMP,
    authorization_code_expires_at timestamp     DEFAULT CURRENT_TIMESTAMP,
    authorization_code_metadata   blob          DEFAULT NULL,
    access_token_value            blob          DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT CURRENT_TIMESTAMP,
    access_token_expires_at       timestamp     DEFAULT CURRENT_TIMESTAMP,
    access_token_metadata         blob          DEFAULT NULL,
    access_token_type             varchar(100)  DEFAULT NULL,
    access_token_scopes           varchar(1000) DEFAULT NULL,
    oidc_id_token_value           blob          DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT CURRENT_TIMESTAMP,
    oidc_id_token_expires_at      timestamp     DEFAULT CURRENT_TIMESTAMP,
    oidc_id_token_metadata        blob          DEFAULT NULL,
    refresh_token_value           blob          DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT CURRENT_TIMESTAMP,
    refresh_token_expires_at      timestamp     DEFAULT CURRENT_TIMESTAMP,
    refresh_token_metadata        blob          DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE oauth2_scope
(
    scope_id    varchar(100) NOT NULL,
    client_id   varchar(100) NOT NULL,
    scope       varchar(100) NOT NULL,
    description varchar(400) DEFAULT '',
    PRIMARY KEY (scope)
)