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
