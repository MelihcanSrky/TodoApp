package server

import (
	"fmt"
	"net/http"
	"os"

	"github.com/MelihcanSrky/TodoApp/internal/backend"
	"github.com/MelihcanSrky/TodoApp/internal/models"
	jwt "github.com/golang-jwt/jwt/v5"
	"github.com/gorilla/mux"
)

func CreateJWT(user *models.User) (string, error) {
	claims := &jwt.MapClaims{
		"username": user.Username,
	}

	secret := os.Getenv("JWT_SECRET")
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)

	return token.SignedString([]byte(secret))
}

func permissionDenied(w http.ResponseWriter) {
	WriteJSON(w, http.StatusForbidden, ApiError{Error: "permission denied"})
}

func AuthWithJWT(handlerFunc http.HandlerFunc, s backend.Backender) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		tokenStr := r.Header.Get("X-jwt-Token")
		token, err := validateJWT(tokenStr)
		if err != nil {
			permissionDenied(w)
			return
		}

		if !token.Valid {
			permissionDenied(w)
			return
		}

		username, err := GetUserName(r)
		if err != nil {
			permissionDenied(w)
			return
		}

		user, err := s.GetUserByUsername(username)
		if err != nil {
			permissionDenied(w)
			return
		}

		claims := token.Claims.(jwt.MapClaims)
		if user.Username != claims["username"] {
			permissionDenied(w)
			return
		}

		if err != nil {
			WriteJSON(w, http.StatusForbidden, ApiError{Error: "invalid token"})
			return
		}

		handlerFunc(w, r)
	}
}

func validateJWT(tokenStr string) (*jwt.Token, error) {
	secret := os.Getenv("JWT_SECRET")
	return jwt.Parse(tokenStr, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("Unexpected signing method: %v", token.Header["alg"])
		}
		return []byte(secret), nil
	})
}

func GetUserName(r *http.Request) (string, error) {
	username := mux.Vars(r)["username"]
	return username, nil
}
