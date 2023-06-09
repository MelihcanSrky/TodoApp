package backend

import "github.com/MelihcanSrky/TodoApp/internal/models"

type Backender interface {
	CreateUserPostgres(user *models.User) error
	GetUserByUsername(userName string) (*models.User, error)
	CreateTodoPostgres(todo *models.TodoItem) error
	GetTodosByUserName(userUuid string) ([]*models.TodoItem, error)
	UpdateTodoPostgres(value bool, uuid string) error
}
