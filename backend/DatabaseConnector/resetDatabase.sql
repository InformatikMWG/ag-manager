DROP TABLE IF EXISTS Filters;
DROP TABLE IF EXISTS Project_slots;
DROP TABLE IF EXISTS Student_in_Group;
DROP TABLE IF EXISTS Student_in_Project;
DROP TABLE IF EXISTS Groups;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Students;


CREATE TABLE Students (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  password   VARCHAR(255),
  classname  VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE Projects (
  id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(255),
  description VARCHAR(255),
  costs       VARCHAR(255),
  location    VARCHAR(255),
  coach       VARCHAR(255),
  supervisor  VARCHAR(255),
  maxNrStudents INT
) ENGINE=InnoDB;


CREATE TABLE Groups (
  id        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255)
) ENGINE=InnoDB;


CREATE TABLE Project_slots (
  pid         INT NOT NULL,
  date        DATE,
  time_start  TIME,
  time_end    TIME,
  FOREIGN KEY (pid) REFERENCES Projects (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
) ENGINE=InnoDB;


CREATE TABLE Student_in_Group (
  sid        VARCHAR(255) NOT NULL,
  gid        INT NOT NULL,
  FOREIGN KEY (sid) REFERENCES Students(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE,
  FOREIGN KEY (gid) REFERENCES Groups(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE
) ENGINE=InnoDB;


CREATE TABLE Student_in_Project (
  sid        VARCHAR(255) NOT NULL,
  pid        INT NOT NULL,
  time       DATETIME,
  state      INT,
  FOREIGN KEY (sid) REFERENCES Students(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE,
  FOREIGN KEY (pid) REFERENCES Projects(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE
) ENGINE=InnoDB;



CREATE TABLE Filters (
  pid       INT,
  gid       INT,
  isBlacklist BOOLEAN
) ENGINE=InnoDB;
