package main

import (
	"github.com/MelihcanSrky/TodoApp/internal/backend/postgresql"
	"log"

	"github.com/MelihcanSrky/TodoApp/internal/db"
	"github.com/MelihcanSrky/TodoApp/server"
)

func main() {
	store, err := db.NewPostgreStore()
	if err != nil {
		log.Fatal(err)
	}
	postgresBackend := postgresql.NewPostgresBackend(store)
	server := server.NewApiServer(":5000", postgresBackend)
	server.Run()
}
