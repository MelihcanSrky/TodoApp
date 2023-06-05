package server

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/MelihcanSrky/TodoApp/internal/models"
	"github.com/gorilla/mux"
)

func (s *ApiServer) HandleTodos(w http.ResponseWriter, r *http.Request) error {
	if r.Method == "POST" {
		return s.HandleCreateTodo(w, r)
	} else if r.Method == "GET" {
		return s.HandleGetTodos(w, r)
	}
	return fmt.Errorf("Method not allowed")
}

func (s *ApiServer) HandleCreateTodo(w http.ResponseWriter, r *http.Request) error {
	req := new(models.CreateTodo)
	if err := json.NewDecoder(r.Body).Decode(req); err != nil {
		return err
	}

	todo, err := models.NewTodo(req)
	if err != nil {
		return err
	}

	if err := s.backend.CreateTodoPostgres(todo); err != nil {
		return err
	}

	return WriteJSON(w, http.StatusOK, todo)
}

func (s *ApiServer) HandleGetTodos(w http.ResponseWriter, r *http.Request) error {
	username := mux.Vars(r)["useruuid"]
	todos, err := s.backend.GetTodosByUserName(username)
	if err != nil {
		return err
	}

	return WriteJSON(w, http.StatusOK, todos)
}
