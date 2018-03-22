package main

import (
	"database/sql"
	"time"
)

const tableNameStudents = "Students"
const tableNameAssignments = "Student_in_Project"
const tableNameProjects = "Projects"
const tableNameGroups = "Groups"
const tableNameStudentsInGroups = "Student_in_Group"
const tableNameFilter = "Filter"


type Connection struct {
	database *sql.DB
}

type Project struct
{
	name string
	description string
	costs string
	location string
	coach string
	superviser string
}


func (c *Connection) Open() {
	//Opening a Connection to a database
	db, err := sql.Open("mysql", "parseTime=true") //Depending on with database is going to be used
	Check(err, true)
	c.database = db
}

func (c *Connection) Close() {
	//Close the Connection to the database
	c.database.Close()
}

func (c *Connection) AddAssignment(studentId int, projectId int, time time.Time, state int) bool {
	//Add the Assignment to the table via the databseconnection
	_, err := c.database.Exec("INSERT INTO " + tableNameAssignments + " VALUES(" + string(studentId) + "," + string(projectId) + ", 0," + string(state) + ");")
	Check(err, true)
	return true
}

func (c *Connection) RemoveAssignment(studentId int, projectId int) bool {
	//Delete the Assignment in the table via the databseconnection
	_, err := c.database.Exec("DELETE FROM " + tableNameAssignments + " WHERE student_id = " + string(studentId) + " AND project_id = " + string(projectId) + ";")
	Check(err, true)
	return true
}

func (c *Connection) MayAssign(studentId int, projectId int) bool {
	//Finds out if a student is allowed to assign to the project
	res, err := c.database.Query("SELECT isBlacklist FROM " +  tableNameFilter + "," + tableNameProjects + "WHERE id = pid")
	Check(err, true)
	var isBlacklist bool
	res.Next()
	err = res.Scan(&isBlacklist)
	Check(err,true)
	if !isBlacklist{
	res, err  = c.database.Query("SELECT COUNT(*) FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE " + tableNameProjects + ".id = " + string(projectId) + " AND " + tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid = " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid AND " +  tableNameStudentsInGroups + ".sid = " + string(studentId) + ";")
	}else{
	res, err  = c.database.Query("SELECT COUNT(*) FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE " + tableNameProjects + ".id = " + string(projectId) + " AND " + tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid <> " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid AND " +  tableNameStudentsInGroups + ".sid = " + string(studentId) + ";")
	}
	Check(err, true)
	number := checkCount(res)
	return (number > 0)
}

func (c *Connection) GetProjectsForStudent(studentId int) []Project {
	//TODO: Return all Projects that can be chosen by the student
	return nil
}


func checkCount(rows *sql.Rows) (count int) {
 	for rows.Next() {
    	err:= rows.Scan(&count)
    	Check(err, true)
    }   
    return count
}