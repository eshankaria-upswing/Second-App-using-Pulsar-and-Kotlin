CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL UNIQUE
);

CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       title TEXT NOT NULL,
                       author TEXT NOT NULL,
                       CONSTRAINT uq_book UNIQUE (title, author)
);

CREATE TABLE inventory (
                           id BIGSERIAL PRIMARY KEY,
                           book_id BIGINT NOT NULL,
                           available_count INT NOT NULL CHECK (available_count >= 0),

                           CONSTRAINT fk_inventory_book
                               FOREIGN KEY (book_id)
                                   REFERENCES books(id)
                                   ON DELETE CASCADE,

                           CONSTRAINT uq_inventory_book UNIQUE (book_id)
);

CREATE TABLE book_requests (
                               id BIGSERIAL PRIMARY KEY,
                               book_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               requested_at TIMESTAMP NOT NULL DEFAULT now(),

                               CONSTRAINT fk_request_book
                                   FOREIGN KEY (book_id)
                                       REFERENCES books(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_request_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT uq_user_book_request UNIQUE (book_id, user_id)
);
