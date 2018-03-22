package main

import (
	"database/sql"
)

const tableNameStudents = "students"
const tableNameAssignments = "assignments"
const tableNameProjects = "projects"

func (c *Connection) Open() {
	//Opening a Connection to a database
	db, err := sql.Open("mysql", "") //Depending on with database is going to be used
	Check(err, true)
	c.database = db
}

type Connection struct {
	database *sql.DB
}

func (c *Connection) Close() {
	//Close the Connection to the database
	c.database.Close()
}

func (c *Connection) addAssignment(student_id int, project_id int) bool {
	//Add the Assignment to the table via the databseconnection
	_, err := c.database.Exec("INSERT INTO " + tableNameAssignments + " VALUES(" + string(student_id) + "," + string(project_id) + ");")
	Check(err, true)
	return true
}

func (c *Connection) removeAssignment(student_id int, project_id int) bool {
	//Delete the Assignment in the table via the databseconnection
	_, err := c.database.Exec("DELDETE FROM " + tableNameAssignments + " WHERE student_id = " + string(student_id) + " AND project_id = " + string(project_id) + ";")
	Check(err, true)
	return true
}

func (c *Connection) getAssignments(student_id int) string {
	//Need to know, what coloums should be needed
	res, err := c.database.Query("SELECT * FROM " + tableNameAssignments + "," + tableNameProjects + " WHERE " + tableNameAssignments + ".project_id = " + tableNameProjects + ".project_id AND student_id = " + string(student_id) + ";")
	Check(err, true)

	defer res.Close()
	for res.Next() {
		//Read out symbols, that are needed
		err := res.Scan()
		Check(err, true)
	}
	err = res.Err()
	Check(err, true)

	return ""
}
