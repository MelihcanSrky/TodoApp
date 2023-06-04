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
