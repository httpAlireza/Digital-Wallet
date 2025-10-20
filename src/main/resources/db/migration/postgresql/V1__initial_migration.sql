CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT cURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    INT          NOT NULL DEFAULT 0
);

CREATE TABLE wallets
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)     NOT NULL,
    currency   VARCHAR(50)      NOT NULL,
    balance    DOUBLE PRECISION NOT NULL,
    user_id    BIGINT           NOT NULL,
    created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    INT              NOT NULL DEFAULT 0,
    CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT uq_user_wallet_name UNIQUE (user_id, name)
);

CREATE TABLE transactions
(
    id             UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
    type           VARCHAR(50)      NOT NULL,
    amount         DOUBLE PRECISION NOT NULL,
    from_wallet_id BIGINT,
    to_wallet_id   BIGINT,
    created_at     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_from_wallet FOREIGN KEY (from_wallet_id) REFERENCES wallets (id),
    CONSTRAINT fk_to_wallet FOREIGN KEY (to_wallet_id) REFERENCES wallets (id)
);
