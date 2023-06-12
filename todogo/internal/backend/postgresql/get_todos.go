package postgresql

import (
	"database/sql"

	"github.com/MelihcanSrky/TodoApp/internal/models"
)

func (s *PostgresStore) GetTodosByUserName(userUuid string) ([]*models.TodoItem, error) {
	rows, err := s.db.Query("select * from todos where user_uuid=$1", userUuid)
	if err != nil {
		return nil, err
	}

	todos := []*models.TodoItem{}
	for rows.Next() {
		todo, err := scanTodos(rows)
		if err != nil {
			return nil, err
		}
		todos = append(todos, todo)
	}
	return todos, nil
}

func scanTodos(rows *sql.Rows) (*models.TodoItem, error) {
	todo := new(models.TodoItem)
	err := rows.Scan(
		&todo.Uuid,
		&todo.UserUuid,
		&todo.Title,
		&todo.Detail,
		&todo.IsChecked,
		&todo.Category,
		&todo.Priority,
		&todo.WeekOfYear,
		&todo.DayOfYear,
		&todo.AssignedAt,
	)
	return todo, err
}
