package main

import (
	"net/http"
	"strconv"
)

type RequestTyp int

const(
	loginRequestTyp RequestTyp = 0
	indexRequestTyp RequestTyp = 1
)

// Test HTTP request handler, sleeps the process for a certain amount of time.
func RequestHandler(w http.ResponseWriter, r *http.Request, jobQueue chan Runner) {
	// Make sure we can only be called with an HTTP POST request.
	if r.Method != "POST" {
		w.Header().Set("Allow", "POST")
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}
	
	typ, err := strconv.Atoi(r.FormValue("typ"))
	Check(err, true)
	typR := RequestTyp(typ)
	studentId := r.FormValue("studentId")

	switch typR {
	case loginRequestTyp:	
		task := &loginRequest{studentId}
		jobQueue <- task
		break
	case indexRequestTyp:
		break
	default:
		break
	}

	// Render success.
	w.WriteHeader(http.StatusCreated)
}

type loginRequest struct {
	studentId string
}

func (r *loginRequest) run() error {
	//TODO: Find allowed projects
	//		Send to client
	return nil
}
