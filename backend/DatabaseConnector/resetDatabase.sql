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


INSERT INTO Students (id, first_name, last_name, password)
VALUES             ('AR1928', 'Adam', 'Riese', '');



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


INSERT INTO Projects (name, description, costs, location, coach)
VALUES             ('Go-Workshop', 'Lerne Go zu spielen!', '0 Euro', '2.18',
'Hr. Sedol');


CREATE TABLE Groups (
  id        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255)
) ENGINE=InnoDB;

INSERT INTO Groups(name)
VALUES ('Q12');


CREATE TABLE Project_slots (
  pid         INT NOT NULL,
  date        DATE,
  time_start  TIME,
  time_end    TIME,
  FOREIGN KEY (pid) REFERENCES Projects (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
) ENGINE=InnoDB;


INSERT INTO Project_slots (pid, date, time_start, time_end)
VALUES             (1, '2018-05-05', '09:00', '09:45');

# Naechste Eingabe sollte nicht funktionieren,
# da kein Projekt mit ID 2 vorhanden
#INSERT INTO Project_slots (pid, date, time_start, time_end)
#VALUES             (2, '2018-05-05', '09:00', '09:45');



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



INSERT INTO Student_in_Group (sid, gid)
VALUES             ('AR1928', 1);


CREATE TABLE Student_in_Project (
  sid        VARCHAR(255) NOT NULL,
  pid        INT NOT NULL,
  time       DATETIME,
  state      INT,
  FOREIGN KEY (sid) REFERENCES Students(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE,
  FOREIGN KEY (pid) REFERENCES Groups(id)
  ON DELETE RESTRICT
  ON UPDATE CASCADE
) ENGINE=InnoDB;



INSERT INTO Student_in_Project (sid, pid, time, state)
VALUES             ('AR1928', 1, '2018-05-05 10:00', 1);



CREATE TABLE Filters (
  pid       INT,
  gid       INT,
  isBlacklist BOOLEAN
) ENGINE=InnoDB;
