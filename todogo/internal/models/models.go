package models

import (
	"time"

	"github.com/google/uuid"
	"golang.org/x/crypto/bcrypt"
)

type CreateUser struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type User struct {
	Uuid     string `json:"uuid"`
	Username string `json:"username"`
	Password string `json:"password"`
}

type UpdateTodo struct {
	Value bool `json:"value"`
}

type LoginRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type LoginResponse struct {
	Token string `json:"token"`
}

func (u *User) ValidPassword(pass string) bool {
	return bcrypt.CompareHashAndPassword([]byte(u.Password), []byte(pass)) == nil
}

func NewUser(username string, password string) (*User, error) {
	uuid := uuid.NewString()
	cryptoPassword, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		return nil, err
	}
	return &User{
		Uuid:     uuid,
		Username: username,
		Password: string(cryptoPassword[:]),
	}, nil
}

type CreateTodo struct {
	Useruuid   string `json:"user_uuid"`
	Title      string `json:"title"`
	Detail     string `json:"detail"`
	Category   string `json:"category"`
	Priority   int    `json:"priority"`
	WeekOfYear int    `json:"week_of_year"`
	DayOfYear  int    `json:"day_of_week"`
	AssignedAt string `json:"assigned_at"`
}

type TodoItem struct {
	Uuid       string    `json:"uuid"`
	UserUuid   string    `json:"user_uuid"`
	Title      string    `json:"title"`
	Detail     string    `json:"detail"`
	IsChecked  bool      `json:"is_checked"`
	Category   string    `json:"category"`
	Priority   int       `json:"priority"`
	WeekOfYear int       `json:"week_of_year"`
	DayOfYear  int       `json:"day_of_week"`
	AssignedAt string    `json:"assigned_at"`
	CreatedAt  time.Time `json:"created_at"`
}

func NewTodo(todo *CreateTodo) (*TodoItem, error) {
	uuid := uuid.NewString()
	return &TodoItem{
		Uuid:       uuid,
		UserUuid:   todo.Useruuid,
		Title:      todo.Title,
		Detail:     todo.Detail,
		IsChecked:  false,
		Category:   todo.Category,
		Priority:   todo.Priority,
		WeekOfYear: todo.WeekOfYear,
		DayOfYear:  todo.DayOfYear,
		AssignedAt: todo.AssignedAt,
	}, nil
}
