package main

import(
"database/sql"
"log"
)

const TableName_Students = "students"
const TableName_Assignments = "assignments"
const TableName_Projects = "projects"

func openDatabase()*sql.DB {
	//Opening a Connection to a database
	db, err := sql.Open("mysql","") //Depending on with database is going to be used
	if err != nil {
		log.Fatal(err)
		return nil
	}
	//A Pointer to the database
	return db;
}


func CloseDatabase(db *sql.DB){
	//Close the Connection to the database
	db.Close()	
}

func addAssignment(student_id int, project_id int, db *sql.DB)bool {
	//Add the Assignment to the table via the databseconnection
	_, err := db.Exec("INSERT INTO "+ TableName_Assignments +" VALUES("+string(student_id)+","+string(project_id)+");")
	if err != nil {
		return false
	}
	return true	
}

func removeAssignment(student_id int, project_id int, db *sql.DB)bool {
	//Delete the Assignment in the table via the databseconnection
	_, err := db.Exec("DELDETE FROM " + TableName_Assignments + " WHERE student_id = " + string(student_id) + " AND project_id = " + string(project_id) + ";")
	if err != nil {
		return false
	}
	return true 
}

func getAssignments(student_id int, db *sql.DB)string {
	//Need to know, what coloums should be needed
	res, err:= db.Query("SELECT * FROM " + TableName_Assignments + "," + TableName_Projects + " WHERE " + TableName_Assignments + ".project_id = " + TableName_Projects + ".project_id AND student_id = " + string(student_id) + ";")
	
	if err != nil {
		log.Fatal(err)
	}
	defer res.Close()
	for res.Next() {
		//Read out symbols, that are needed
		err := res.Scan()
		if err != nil {
			log.Fatal(err)
			return
		}
	}
	err = rows.Err()
	if err != nil {
		log.Fatal(err)
		return
	}	
}
