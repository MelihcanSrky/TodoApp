package server

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/MelihcanSrky/TodoApp/internal/models"
)

func (s *ApiServer) HandleUser(w http.ResponseWriter, r *http.Request) error {
	if r.Method == "POST" {
		return s.HandleCreateUser(w, r)
	}
	return fmt.Errorf("Method not allowed!")
}

func (s *ApiServer) HandleCreateUser(w http.ResponseWriter, r *http.Request) error {
	req := new(models.CreateUser)
	if err := json.NewDecoder(r.Body).Decode(req); err != nil {
		return err
	}

	user, err := models.NewUser(req.Username, req.Password)
	if err != nil {
		return err
	}
	if err := s.backend.CreateUserPostgres(user); err != nil {
		return err
	}

	return WriteJSON(w, http.StatusOK, user)
}

func (s *ApiServer) HandleLoginUser(w http.ResponseWriter, r *http.Request) error {
	if r.Method != "POST" {
		return fmt.Errorf("method not allowed")
	}
	var req models.LoginRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		return err
	}

	user, err := s.backend.GetUserByUsername(req.Username)
	if err != nil {
		return err
	}

	if !user.ValidPassword(req.Password) {
		return fmt.Errorf("not authenticated")
	}

	token, err := CreateJWT(user)
	if err != nil {
		return err
	}

	res := models.LoginResponse{
		Token: token,
	}
	return WriteJSON(w, http.StatusOK, res)
}

func (s *ApiServer) HandleGetUserByUserName(w http.ResponseWriter, r *http.Request) error {
	username, err := GetUserName(r)
	if err != nil {
		return err
	}

	user, err := s.backend.GetUserByUsername(username)
	if err != nil {
		return err
	}

	return WriteJSON(w, http.StatusOK, user)
}
