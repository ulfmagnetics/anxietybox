DROP TABLE IF EXISTS box CASCADE;
DROP TABLE IF EXISTS anxiety CASCADE;
DROP TABLE IF EXISTS reply CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- A user's box (i.e. account)
CREATE TABLE box (
       created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       count INT NOT NULL DEFAULT 0,
       confirm uuid DEFAULT UUID_GENERATE_V4(),
       active BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX lower_email_index ON box (lower(email));
CREATE UNIQUE INDEX confirm_index ON box (confirm);

-- Track anxieties
CREATE TABLE anxiety (
       id SERIAL PRIMARY KEY,
       box_id int REFERENCES box(id) ON DELETE CASCADE,
       tracker uuid DEFAULT UUID_GENERATE_V4(),
       description text UNIQUE NOT NULL
);

CREATE UNIQUE INDEX track_index ON box (confirm);

-- Track replies
CREATE TABLE reply (
       created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
       id SERIAL PRIMARY KEY,
       box_id int REFERENCES box(id),
       anxiety_id int REFERENCES anxiety(id) ON DELETE CASCADE,
       description text
);
