-- TABLE: s_user
DROP SEQUENCE IF EXISTS s_user_user_id_seq cascade;
CREATE SEQUENCE s_user_user_id_seq;
DROP TABLE IF EXISTS s_user;
CREATE TABLE s_user (
	user_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('s_user_user_id_seq'),
	u_role VARCHAR(100) NOT NULL,
	user_name VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS s_user_user_id_seq OWNED BY s_user.user_id;

-- TABLE: s_rule
DROP SEQUENCE IF EXISTS s_rule_rule_id_seq cascade;
CREATE SEQUENCE s_rule_rule_id_seq;
DROP TABLE IF EXISTS s_rule;
CREATE TABLE s_rule (
	rule_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('s_rule_rule_id_seq'),
	min_age BIGINT NOT NULL,
	max_age BIGINT NOT NULL,
	test_15_rate BIGINT NOT NULL,
	test_45_rate BIGINT NOT NULL,
	final_exam_rate BIGINT NOT NULL,
	first_sem_rate BIGINT NOT NULL,
	second_sem_rate BIGINT NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS s_rule_rule_id_seq OWNED BY s_rule.rule_id;

-- TABLE: class_type
DROP SEQUENCE IF EXISTS class_type_class_type_id_seq cascade;
CREATE SEQUENCE class_type_class_type_id_seq;
DROP TABLE IF EXISTS class_type;
CREATE TABLE class_type (
	class_type_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('class_type_class_type_id_seq'),
	class_index BIGINT NOT NULL,
	class_type_code VARCHAR(100) NOT NULL,
	class_type_name VARCHAR(255) NOT NULL,
	description VARCHAR(4000),
	note VARCHAR(4000),
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS class_type_class_type_id_seq OWNED BY class_type.class_type_id;

-- TABLE: s_class
DROP SEQUENCE IF EXISTS s_class_class_id_seq cascade;
CREATE SEQUENCE s_class_class_id_seq;
DROP TABLE IF EXISTS s_class;
CREATE TABLE s_class (
	class_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('s_class_class_id_seq'),
	class_type_id BIGINT NOT NULL,
	class_code VARCHAR(100) NOT NULL,
	class_name VARCHAR(255) NOT NULL,
	description VARCHAR(4000),
	note VARCHAR(4000) ,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS s_class_class_id_seq OWNED BY s_class.class_id;

-- TABLE: student
DROP SEQUENCE IF EXISTS student_student_id_seq cascade;
CREATE SEQUENCE student_student_id_seq;
DROP TABLE IF EXISTS student;
CREATE TABLE student (
	student_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('student_student_id_seq'),
	class_id BIGINT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	birth_date DATE NOT NULL,
	gender BIGINT NOT NULL,
	address VARCHAR(500) NOT NULL,
	email VARCHAR(255) NOT NULL,
	description VARCHAR(4000),
	note VARCHAR(4000) ,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS student_student_id_seq OWNED BY student.student_id;

-- TABLE: subject
DROP SEQUENCE IF EXISTS subject_subject_id_seq cascade;
CREATE SEQUENCE subject_subject_id_seq;
DROP TABLE IF EXISTS subject;
CREATE TABLE subject (
	subject_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('subject_subject_id_seq'),
	subject_code VARCHAR(100) NOT NULL,
	subject_name VARCHAR(255) NOT NULL,
	avg_score DECIMAL(5,2) NOT NULL,
	description VARCHAR(4000),
	note VARCHAR(4000) ,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS subject_subject_id_seq OWNED BY subject.subject_id;

-- TABLE: score
DROP SEQUENCE IF EXISTS score_score_id_seq cascade;
CREATE SEQUENCE score_score_id_seq;
DROP TABLE IF EXISTS score;
CREATE TABLE score (
	score_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('score_score_id_seq'),
	student_id BIGINT NOT NULL,
	subject_id BIGINT NOT NULL,
	semester BIGINT NOT NULL,
	score_type BIGINT NOT NULL,
	score DECIMAL(5,2) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS score_score_id_seq OWNED BY score.score_id;

INSERT INTO public.s_user(
	user_id, u_role, user_name, password, first_name, last_name, email, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (0, 0, 'admin', 'admin', 'admin', 'admin', 'admin@gmail.com', 'false', '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);
INSERT INTO public.s_rule(
	rule_id, min_age, max_age, test_15_rate, test_45_rate, final_exam_rate, first_sem_rate, second_sem_rate, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (1, 14, 17, 20, 30, 50, 30, 70, 'false', '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);





