package main

import (
	"database/sql"
	_ "ag-manager/go-sql-driver/mysql"
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

type Project struct{
	id int
	name string
	description string
	costs string
	location string
	coach string
	superviser string
	maxNrStudents int
}


func (c *Connection) Open(username string, password string, dbname string, adress string) {
	//Opening a Connection to a database 
	//[username[:password]@][protocol[(address)]]/dbname[?param1=value1&...&paramN=valueN]
	db, err := sql.Open("mysql", username + ":" + password +"@tcp("+adress+")/" + dbname +"?parseTime=true") //Depending on with database is going to be used
	Check(err, true)
	c.database = db
}

func (c *Connection) Close() {
	//Close the Connection to the database
	c.database.Close()
}

func (c *Connection) AddAssignment(studentId int, projectId int, state int) error {
	//Add the Assignment to the table via the databseconnection
	_, err := c.database.Exec("INSERT INTO " + tableNameAssignments + " VALUES(" + string(studentId) + "," + string(projectId) + ", 0," + string(state) + ");")
	return err
}

func (c *Connection) RemoveAssignment(studentId int, projectId int) error {
	//Delete the Assignment in the table via the databseconnection
	_, err := c.database.Exec("DELETE FROM " + tableNameAssignments + " WHERE student_id = " + string(studentId) + " AND project_id = " + string(projectId) + ";")
	return err
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
	assign := checkCount(res) > 0
	if !assign{
		return false
	}
	res, err  = c.database.Query("SELECT COUNT("+ tableNameStudents+".id) FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE " + tableNameProjects + ".id = " + string(projectId) + " AND " + tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid = " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid;")
	Check(err,true)
	number := checkCount(res)
	res, err = c.database.Query("SELECT maxNrStudents FROM " + tableNameProjects + "WHERE id = " + string(projectId) + ";")
	Check(err,true)
	numberMax := checkCount(res)
	return number < numberMax
}

func (c *Connection) GetProjectsForStudent(studentId int) []Project {
	var projects[]Project
	res, err := c.database.Query("SELECT DISTINCT "+ tableNameProjects +".id, name, discription, costs, location, coach, superviser, maxNrStudents FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE "+ tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid = " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid AND " +  tableNameStudentsInGroups + ".sid = " + string(studentId) + "AND isBlacklist = true;")
	Check(err, true)
	for res.Next(){
		var project Project
		err = res.Scan(&project.id, &project.name, &project.description, &project.costs, &project.location, &project.coach, &project.superviser, &project.maxNrStudents)
		
		res, err  = c.database.Query("SELECT COUNT("+ tableNameStudents+".id) FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE " + tableNameProjects + ".id = " + string(project.id) + " AND " + tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid = " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid;")
		Check(err,true)
		number := checkCount(res)
		res, err := c.database.Query("SELECT maxNrStudents FROM " + tableNameProjects + "WHERE id = " + string(project.id) + ";")
		Check(err,true)
		numberMax := checkCount(res)
		if number < numberMax{
			projects = append(projects,project)
			}
	}
	Check(err, true)

	res, err = c.database.Query("SELECT DISTINCT"+ tableNameProjects +".id, name, discription, costs, location, coach, superviser, maxNrStudents FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE "+ tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid <> " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid AND " +  tableNameStudentsInGroups + ".sid = " + string(studentId) + "AND isBlacklist = false;")
	Check(err, true)
	for res.Next(){
		var project Project
		err = res.Scan(&project.id, &project.name, &project.description, &project.costs, &project.location, &project.coach, &project.superviser, &project.maxNrStudents)
		
		res, err  = c.database.Query("SELECT COUNT("+ tableNameStudents+".id) FROM " + tableNameProjects + "," + tableNameGroups + "," + tableNameStudentsInGroups + "," + tableNameFilter + "," + "WHERE " + tableNameProjects + ".id = " + string(project.id) + " AND " + tableNameProjects +".id = " +  tableNameFilter + ".pid AND " + tableNameFilter + ".gid = " + tableNameGroups + ".id AND " +tableNameGroups + ".id = " + tableNameStudentsInGroups + ".gid;")
		Check(err,true)
		number := checkCount(res)
		res, err := c.database.Query("SELECT maxNrStudents FROM " + tableNameProjects + "WHERE id = " + string(project.id) + ";")
		Check(err,true)
		numberMax := checkCount(res)
		if number < numberMax{
			projects = append(projects,project)
			}
	}
	Check(err, true)
	return projects
}


func checkCount(rows *sql.Rows) (count int) {
 	for rows.Next() {
    	err:= rows.Scan(&count)
    	Check(err, true)
    }   
    return count
}