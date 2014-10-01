CREATE TABLE kborrow (
	setting_name TEXT,
	value INTEGER
);

CREATE TABLE article (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	description TEXT
);

CREATE TABLE lender (
	id INTEGER PRIMARY KEY,
	name TEXT,
	surname TEXT,
	student_number INT,
	comment TEXT
);

CREATE TABLE user (
	id INTEGER PRIMARY KEY,
	name TEXT,
	surname TEXT
);

CREATE TABLE lending (
	id INTEGER PRIMARY KEY,
	article_id INT,
	user_id INT,
	lender_id INT,
	start_date DATE DEFAULT current_date,
	expected_end_date DATE,
	end_date DATE,
	comment
);

insert into kborrow
	(setting_name, value)
	VALUES
	('is_configured', 1)
;