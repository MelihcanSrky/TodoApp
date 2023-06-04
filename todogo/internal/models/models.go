package models

import (
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

type LoginRequest struct {
	Username string `json:"username"`
	Password string `json:"password`
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

type TodoItem struct {
	Uuid       string `json:"uuid"`
	UserUuid   string `json:"user_uuid"`
	Title      string `json:"title"`
	Detail     string `json:"detail"`
	LastAt     int    `json:"last_at"`
	IsChecked  bool   `json:"is_checked"`
	Category   string `json:"category"`
	Priority   int    `json:"priority"`
	AssignedAt int    `json:"assigned_at"`
}
