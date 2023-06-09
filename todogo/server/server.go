package server

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/MelihcanSrky/TodoApp/internal/backend"
	"github.com/gorilla/mux"
)

type ApiServer struct {
	listenAddr string
	backend    backend.Backender
}

func NewApiServer(listenAddr string, backend backend.Backender) *ApiServer {
	return &ApiServer{
		listenAddr: listenAddr,
		backend:    backend,
	}
}

func (s *ApiServer) Run() {
	router := mux.NewRouter()

	router.HandleFunc("/api/user", makeHttpHandleFunc(s.HandleUser))
	router.HandleFunc("/api/user/login", makeHttpHandleFunc(s.HandleLoginUser))
	router.HandleFunc("/api/user/{username}", AuthWithJWT(makeHttpHandleFunc(s.HandleGetUserByUserName), s.backend))
	router.HandleFunc("/api/todos/{username}/{useruuid}", AuthWithJWT(makeHttpHandleFunc(s.HandleTodos), s.backend))
	router.HandleFunc("/api/todos/update/{username}/{uuid}", AuthWithJWT(makeHttpHandleFunc(s.HandleUpdateTodo), s.backend))
	log.Println("TodoApp server is running now on port: ", s.listenAddr)

	http.ListenAndServe(s.listenAddr, router)
}

func WriteJSON(w http.ResponseWriter, status int, v any) error {
	w.Header().Add("Content-type", "application/json")
	w.WriteHeader(status)
	return json.NewEncoder(w).Encode(v)
}

func makeHttpHandleFunc(f apiFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		if err := f(w, r); err != nil {
			WriteJSON(w, http.StatusBadRequest, ApiError{Error: err.Error()})
		}
	}
}

type apiFunc func(http.ResponseWriter, *http.Request) error

type ApiError struct {
	Error string `json:"error"`
}
