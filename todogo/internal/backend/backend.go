package backend

import "github.com/MelihcanSrky/TodoApp/internal/models"

type Backender interface {
	CreateUserPostgres(user *models.User) error
}
