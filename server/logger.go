package main

import (
	"fmt"
	"io"
	"os"
	"path/filepath"
	"runtime"
	"time"
)

var w io.Writer

func init() {
	f, e := os.OpenFile("server.log", os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
	if e != nil {
		fmt.Printf("[%v helpers.go:17] Error opening logfile, skipping: %v", time.Now().Format("2006/01/02 15:04:05"), e)
		w = os.Stdout
	} else {
		w = io.MultiWriter(os.Stdout, f)
	}
}

func getLogPrefix(d int) string {
	_, f, l, _ := runtime.Caller(d)
	return fmt.Sprintf("[%v %v:%v]", time.Now().Format("2006/01/02 15:04:05"), filepath.Base(f), l)
}

func logRaw(a ...interface{}) (int, error) {
	return fmt.Fprintln(w, a...)
}

// Log prints a line to Log output.
func Log(a ...interface{}) (int, error) {
	return logRaw(append([]interface{}{getLogPrefix(2)}, a...)...)
}

// Check tests for an error and prints it to log if e is not nil. If p is true, the program will panic.
func Check(e error, p bool) {
	if e != nil {
		if p {
			logRaw(getLogPrefix(2), e)
			panic(e)
		} else {
			logRaw(getLogPrefix(2), e)
		}
	}
}
