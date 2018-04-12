package main

import (
	"fmt"
	"net/http"
	"time"
)

type Request int

const(
	loginRequest Request = 0
	indexRequest Request = 1
)

// Test HTTP request handler, sleeps the process for a certain amount of time.
func RequestHandler(w http.ResponseWriter, r *http.Request, jobQueue chan Runner) {
	// Make sure we can only be called with an HTTP POST request.
	if r.Method != "POST" {
		w.Header().Set("Allow", "POST")
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}
	var typ Request
	typ := r.FromValue("typ")
	
	switch typ {
	case loginRequest:	
		task := &loginRequest{studentId}
		jobQueue <- task
		break
	case indexRequest:
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
