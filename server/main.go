package main

import (
	"flag"
	"net/http"
)

func main() {
	var (
		maxQueueSize = flag.Int("max_queue_size", 100, "The size of job queue")
		maxWorkers   = flag.Int("max_workers", 5, "The number of workers to start")
		port         = flag.String("port", "8080", "The server port")
	)
	flag.Parse()

	jobQueue := make(chan Runner, *maxQueueSize)

	wm := NewWorkerManager(jobQueue, *maxWorkers)
	wm.Run()

	//tm := NewTemplateManager("a","b","c")//TemplateManager
	
	// Test work distribution system by pausing each worker for x seconds specified by request.
	http.HandleFunc("/work", func(w http.ResponseWriter, r *http.Request) {
		sleepRequestHandler(w, r, jobQueue)
	})
	Check(http.ListenAndServe(":"+*port, nil), true)
}
