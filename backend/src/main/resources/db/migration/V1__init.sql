CREATE TABLE `teacher` (
  id int(32) AUTO_INCREMENT PRIMARY KEY,
  teacher_number varchar(32) NOT NULL DEFAULT '',
  name varchar(20) NOT NULL,
  birth_date datetime(6) NOT NULL,
  gender bit NOT NULL,
  native_place varchar(255) NOT NULL,
  national varchar(32) NOT NULL,
  phone_number varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX teacher_number_index ON teacher(teacher_number);

CREATE TABLE `team` (
  id int(32) AUTO_INCREMENT PRIMARY KEY,
  school_year datetime(6),
  professional varchar(20) NOT NULL,
  class_number varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `student` (
  id int(32) AUTO_INCREMENT PRIMARY KEY,
  student_number varchar(32) NOT NULL,
  name varchar(20) NOT NULL,
  birth_date datetime(6) NOT NULL,
  gender bit NOT NULL,
  native_place varchar(255) NOT NULL,
  national varchar(32) NOT NULL,
  phone_number varchar(20) NOT NULL,
  team_id int(32) NOT NULL,
  foreign key (team_id) references team (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX student_number11 ON student(student_number);

CREATE TABLE `course` (
  id int(32) AUTO_INCREMENT PRIMARY KEY,
  semester varchar(20) NOT NULL,
  start_at datetime(6) NOT NULL,
  name varchar(20) NOT NULL,
  description varchar(255) NOT NULL,
  team_id int(32) NOT NULL,
  teacher_id int(32) NOT NULL,
  foreign key (team_id) references team (id),
  foreign key (teacher_id) references teacher (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `account` (
  id int(32) AUTO_INCREMENT PRIMARY KEY,
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO account(username, password) values ('admin', 'admin');
