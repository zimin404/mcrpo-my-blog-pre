CREATE TABLE posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    text TEXT,
    likes_count INT DEFAULT 0
);

CREATE TABLE comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT,
    text TEXT
);
