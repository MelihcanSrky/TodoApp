package postgresql

func (s *PostgresStore) UpdateTodoPostgres(value bool, uuid string) error {
	_, err := s.db.Query(`
	update todos
	set is_checked=$1
	where uuid=$2
	`,
		value, uuid,
	)
	if err != nil {
		return err
	}
	return nil
}
