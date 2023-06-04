package postgresql

import (
	"database/sql"
	"github.com/MelihcanSrky/TodoApp/internal/backend"
)

type PostgresStore struct {
	db *sql.DB
}

func NewPostgresBackend(db *sql.DB) backend.Backender {
	return &PostgresStore{
		db: db,
	}
}
