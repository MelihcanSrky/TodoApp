package postgresql

import "github.com/MelihcanSrky/TodoApp/internal/models"

func (s *PostgresStore) CreateTodoPostgres(todo *models.TodoItem) error {
	_, err := s.db.Query(`
	insert into todos (
		uuid,
		user_uuid,
		title,
		detail,
		last_at,
		is_checked,
		category,
		priority,
		assigned_at
	) values ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	`,
		todo.Uuid,
		todo.UserUuid,
		todo.Title,
		todo.Detail,
		todo.LastAt,
		todo.IsChecked,
		todo.Category,
		todo.Priority,
		todo.AssignedAt,
	)
	if err != nil {
		return err
	}
	return nil
}
