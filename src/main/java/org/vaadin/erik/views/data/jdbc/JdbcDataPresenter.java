package org.vaadin.erik.views.data.jdbc;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.views.data.DataPresenter;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JdbcDataPresenter implements DataPresenter<PersonDTO> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PersonDTORowMapper personDtoRowMapper = new PersonDTORowMapper();

    public JdbcDataPresenter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Class<PersonDTO> getImplementationClass() {
        return PersonDTO.class;
    }

    @Override
    public Stream<PersonDTO> fetch(Query<PersonDTO, Void> query) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("offset", query.getOffset())
                .addValue("limit", query.getLimit());
        return jdbcTemplate.query(
                String.format("SELECT id, first_name, last_name, email, phone, date_of_birth, occupation, important " +
                        "FROM person %s LIMIT :limit OFFSET :offset", buildSortString(query)),
                parameterSource,
                personDtoRowMapper).stream();
    }

    @Override
    public Optional<PersonDTO> reload(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId());
        return jdbcTemplate.queryForStream(
                "SELECT id, first_name, last_name, email, phone, date_of_birth, occupation, important " +
                        "FROM person WHERE id = :id",
                parameterSource,
                personDtoRowMapper).findFirst();
    }

    @Override
    public void updateOrInsert(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId())
                .addValue("firstName", person.getFirstName())
                .addValue("lastName", person.getLastName())
                .addValue("email", person.getEmail())
                .addValue("phone", person.getPhone())
                .addValue("dateOfBirth", person.getDateOfBirth())
                .addValue("occupation", person.getOccupation())
                .addValue("important", person.isImportant());
        if (person.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO person (first_name, last_name, email, phone, date_of_birth, occupation, important) " +
                            "VALUES (:firstName, :lastName, :email, :phone, :dateOfBirth, :occupation, :important)",
                    parameterSource);
        } else {
            jdbcTemplate.update(
                    "UPDATE person " +
                            "SET first_name = :firstName, last_name = :lastName, email = :email, phone = :phone, " +
                            "date_of_birth = :dateOfBirth, occupation = :occupation, important = :important " +
                            "WHERE id = :id", parameterSource);
        }
    }

    @Override
    public PersonDTO instantiateEmpty() {
        return new PersonDTO();
    }

    @Override
    public boolean isImportant(PersonDTO person) {
        return person.isImportant();
    }

    static class PersonDTORowMapper implements RowMapper<PersonDTO> {

        @Override
        public PersonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(rs.getInt(1));
            personDTO.setFirstName(rs.getString(2));
            personDTO.setLastName(rs.getString(3));
            personDTO.setEmail(rs.getString(4));
            personDTO.setPhone(rs.getString(5));
            personDTO.setDateOfBirth(Optional.ofNullable(rs.getDate(6)).map(Date::toLocalDate).orElse(null));
            personDTO.setOccupation(rs.getString(7));
            personDTO.setImportant(rs.getBoolean(8));
            return personDTO;
        }
    }

    private String buildSortString(Query<?, ?> query) {
        if (query.getSortOrders().isEmpty()) {
            return "";
        }
        return "ORDER BY " + query.getSortOrders().stream().map(sortOrder -> {
            String column;
            switch (sortOrder.getSorted()) {
                case "firstName":
                    column = "first_name";
                    break;
                case "lastName":
                    column = "last_name";
                    break;
                case "email":
                    column = "email";
                    break;
                case "phone":
                    column = "phone";
                    break;
                case "dateOfBirth":
                    column = "date_of_birth";
                    break;
                case "occupation":
                    column = "occupation";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown property to sort by: " + sortOrder.getSorted());
            }
            return String.format("%s %s", column, sortOrder.getDirection() == SortDirection.ASCENDING ? "asc" : "desc");
        }).collect(Collectors.joining(", "));
    }
}
