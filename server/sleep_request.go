package main

import (
	"fmt"
	"net/http"
	"time"
)

func sleepRequestHandler(w http.ResponseWriter, r *http.Request, jobQueue chan Runner) {
	// Make sure we can only be called with an HTTP POST sleepRequest.
	if r.Method != "POST" {
		w.Header().Set("Allow", "POST")
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}

	// Parse the delay.
	delay, err := time.ParseDuration(r.FormValue("delay"))
	if err != nil {
		http.Error(w, "Bad delay value: "+err.Error(), http.StatusBadRequest)
		return
	}

	// Validate delay is in range 1 to 10 seconds.
	if delay.Seconds() < 1 || delay.Seconds() > 10 {
		http.Error(w, "The delay must be between 1 and 10 seconds, inclusively.", http.StatusBadRequest)
		return
	}

	// Set name and validate value.
	name := r.FormValue("name")
	if name == "" {
		http.Error(w, "You must specify a name.", http.StatusBadRequest)
		return
	}

	// Create Job and push the work onto the jobQueue.
	task := &sleepRequest{name: name, duration: delay}
	jobQueue <- task

	// Render success.
	w.WriteHeader(http.StatusCreated)
}

type sleepRequest struct {
	name     string
	duration time.Duration
}

func (r *sleepRequest) run() error {
	fmt.Printf("%s...", r.name)
	time.Sleep(r.duration)
	return nil
}
