package postgresql

import (
	"database/sql"
	"fmt"

	"github.com/MelihcanSrky/TodoApp/internal/models"
)

func (s *PostgresStore) GetUserByUsername(userName string) (*models.User, error) {
	rows, err := s.db.Query("select * from users where username=$1", userName)
	if err != nil {
		return nil, err
	}

	for rows.Next() {
		return scanIntoUser(rows)
	}

	return nil, fmt.Errorf("user not found!")
}

func scanIntoUser(rows *sql.Rows) (*models.User, error) {
	user := new(models.User)
	err := rows.Scan(
		&user.Uuid,
		&user.Username,
		&user.Password,
	)

	return user, err
}
