package postgresql

import (
	"github.com/MelihcanSrky/TodoApp/internal/models"
)

func (s *PostgresStore) CreateUserPostgres(user *models.User) error {
	_, err := s.db.Query(`
	insert into users (
		uuid,
		username,
		password
	) values ($1, $2, $3)
	`, user.Uuid, user.Username, user.Password)

	if err != nil {
		return err
	}

	return nil
}
