package main

import (
	"log"
)

// Runner is the interface that wraps the Run() method.
type Runner interface {
	run() error
}

// newWorker creates a new worker
func newWorker(workerPool chan chan Runner) worker {
	return worker{
		workerPool: workerPool,
		jobQueue:   make(chan Runner),
		quitChan:   make(chan bool),
	}
}

// worker holds the attributes to control behaviour of workers.
type worker struct {
	jobQueue   chan Runner
	workerPool chan chan Runner
	quitChan   chan bool
}

func (w worker) start() {
	go func() {
		for {
			w.workerPool <- w.jobQueue

			select {
			case Runner := <-w.jobQueue:
				if err := Runner.run(); err != nil {
					log.Printf("Error running Runner: %s\n", err.Error())
				}
			case <-w.quitChan:
				return
			}
		}
	}()
}

func (w worker) stop() {
	go func() {
		w.quitChan <- true
	}()
}

// NewDispatcher creates a new work dispatcher.
func NewDispatcher(jobQueue chan Runner, maxWorkers int) *Dispatcher {
	workerPool := make(chan chan Runner, maxWorkers)

	return &Dispatcher{
		jobQueue:   jobQueue,
		maxWorkers: maxWorkers,
		workerPool: workerPool,
	}
}

// Dispatcher manages workers and distributes work.
type Dispatcher struct {
	workerPool chan chan Runner
	maxWorkers int
	jobQueue   chan Runner
}

// Run initializes the dispatcher and starts it.
func (d *Dispatcher) Run() {
	for i := 0; i < d.maxWorkers; i++ {
		worker := newWorker(d.workerPool)
		worker.start()
	}

	go d.dispatch()
}

func (d *Dispatcher) dispatch() {
	for {
		select {
		case Runner := <-d.jobQueue:
			go func() {
				workerJobQueue := <-d.workerPool
				workerJobQueue <- Runner
			}()
		}
	}
}
