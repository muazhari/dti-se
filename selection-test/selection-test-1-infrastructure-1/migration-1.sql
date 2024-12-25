-- extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- ddl
DROP TABLE IF EXISTS account CASCADE;
CREATE TABLE account (
    id UUID PRIMARY KEY,
    name TEXT,
    email TEXT UNIQUE,
    password TEXT,
    phone TEXT,
    dob TIMESTAMPTZ,
    profile_image_url TEXT
);

DROP TABLE IF EXISTS session CASCADE;
CREATE TABLE session (
    id UUID PRIMARY KEY,
    account_id UUID REFERENCES account(id) ON DELETE CASCADE ON UPDATE CASCADE,
    access_token TEXT UNIQUE,
    refresh_token TEXT UNIQUE,
    access_token_expired_at TIMESTAMPTZ,
    refresh_token_expired_at TIMESTAMPTZ
);


-- dml
INSERT INTO account (id, name, email, password, phone, dob, profile_image_url) VALUES
(uuid_generate_v4(), 'admin', 'admin@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456789', '1990-01-01', 'https://placehold.co/400x400?text=account0'),
(uuid_generate_v4(), 'Beth', 'beth@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456780', '1990-02-01', 'https://placehold.co/400x400?text=account1'),
(uuid_generate_v4(), 'Charles', 'charles@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456781', '1990-03-01', 'https://placehold.co/400x400?text=account2'),
(uuid_generate_v4(), 'Diana', 'diana@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456782', '1990-04-01', 'https://placehold.co/400x400?text=account3'),
(uuid_generate_v4(), 'Edward', 'edward@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456783', '1990-05-01', 'https://placehold.co/400x400?text=account4'),
(uuid_generate_v4(), 'Fiona', 'fiona@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456784', '1990-06-01', 'https://placehold.co/400x400?text=account5'),
(uuid_generate_v4(), 'George', 'george@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456785', '1990-07-01', 'https://placehold.co/400x400?text=account6'),
(uuid_generate_v4(), 'Hannah', 'hannah@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456786', '1990-08-01', 'https://placehold.co/400x400?text=account7'),
(uuid_generate_v4(), 'Isaac', 'isaac@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456787', '1990-09-01', 'https://placehold.co/400x400?text=account8'),
(uuid_generate_v4(), 'Jasmine', 'jasmine@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456788', '1990-10-01', 'https://placehold.co/400x400?text=account9'),
(uuid_generate_v4(), 'Kevin', 'kevin@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456789', '1990-11-01', 'https://placehold.co/400x400?text=account10'),
(uuid_generate_v4(), 'Lila', 'lila@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456790', '1990-12-01', 'https://placehold.co/400x400?text=account11'),
(uuid_generate_v4(), 'Mark', 'mark@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456791', '1990-01-02', 'https://placehold.co/400x400?text=account12'),
(uuid_generate_v4(), 'Nina', 'nina@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456792', '1990-02-02', 'https://placehold.co/400x400?text=account13'),
(uuid_generate_v4(), 'Owen', 'owen@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456793', '1990-03-02', 'https://placehold.co/400x400?text=account14'),
(uuid_generate_v4(), 'Paula', 'paula@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456794', '1990-04-02', 'https://placehold.co/400x400?text=account15'),
(uuid_generate_v4(), 'Quinn', 'quinn@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456795', '1990-05-02', 'https://placehold.co/400x400?text=account16'),
(uuid_generate_v4(), 'Rita', 'rita@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456796', '1990-06-02', 'https://placehold.co/400x400?text=account17'),
(uuid_generate_v4(), 'Sam', 'sam@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456797', '1990-07-02', 'https://placehold.co/400x400?text=account18'),
(uuid_generate_v4(), 'Tina', 'tina@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456798', '1990-08-02','https://placehold.co/400x400?text=account19'),
(uuid_generate_v4(), 'Ursula', 'ursula@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456799', '1990-09-02', 'https://placehold.co/400x400?text=account20'),
(uuid_generate_v4(), 'Victor', 'victor@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456800', '1990-10-02', 'https://placehold.co/400x400?text=account21'),
(uuid_generate_v4(), 'Wendy', 'wendy@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456801', '1990-11-02', 'https://placehold.co/400x400?text=account22'),
(uuid_generate_v4(), 'Xander', 'xander@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456802', '1990-12-02', 'https://placehold.co/400x400?text=account23'),
(uuid_generate_v4(), 'Yasmine', 'yasmine@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456803', '1991-01-01', 'https://placehold.co/400x400?text=account24'),
(uuid_generate_v4(), 'Zack', 'zack@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456804', '1991-02-01', 'https://placehold.co/400x400?text=account25'),
(uuid_generate_v4(), 'Alicia', 'alicia@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456805', '1991-03-01', 'https://placehold.co/400x400?text=account26'),
(uuid_generate_v4(), 'Brian', 'brian@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456806', '1991-04-01', 'https://placehold.co/400x400?text=account27'),
(uuid_generate_v4(), 'Cindy', 'cindy@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456807', '1991-05-01', 'https://placehold.co/400x400?text=account28'),
(uuid_generate_v4(), 'David', 'david@mail.com', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', '08123456808', '1991-06-01', 'https://placehold.co/400x400?text=account29');

INSERT INTO session (id, account_id, access_token, refresh_token, access_token_expired_at, refresh_token_expired_at)
SELECT uuid_generate_v4(), id, uuid_generate_v4(), uuid_generate_v4(), now() + interval '1 hour', now() + interval '1 day' 
FROM account;

-- dql
SELECT * 
FROM account
INNER JOIN session ON session.account_id = account.id
WHERE account.id in (SELECT id FROM account LIMIT 1 OFFSET 0)
LIMIT 1 OFFSET 0;
