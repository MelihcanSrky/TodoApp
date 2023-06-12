package postgresql

import "github.com/MelihcanSrky/TodoApp/internal/models"

func (s *PostgresStore) CreateTodoPostgres(todo *models.TodoItem) error {
	_, err := s.db.Query(`
	insert into todos (
		uuid,
		user_uuid,
		title,
		detail,
		is_checked,
		category,
		priority,
		week_of_year,
		day_of_week,
		assigned_at
	) values ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
	`,
		todo.Uuid,
		todo.UserUuid,
		todo.Title,
		todo.Detail,
		todo.IsChecked,
		todo.Category,
		todo.Priority,
		todo.WeekOfYear,
		todo.DayOfYear,
		todo.AssignedAt,
	)
	if err != nil {
		return err
	}
	return nil
}
