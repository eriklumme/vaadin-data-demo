package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.vaadin.erik.data.dto.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Data: JDBC")
public class JdbcDataView extends AbstractDataView<PersonDTO> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PersonDTORowMapper personDtoRowMapper = new PersonDTORowMapper();

    public JdbcDataView(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    Class<PersonDTO> getImplementationClass() {
        return PersonDTO.class;
    }

    @Override
    Stream<PersonDTO> fetch(Query<PersonDTO, Void> query) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("offset", query.getOffset())
                .addValue("limit", query.getLimit());
        return jdbcTemplate.queryForStream(
                "SELECT id, first_name, last_name, email, phone, date_of_birth, occupation, important " +
                        "FROM person LIMIT :limit OFFSET :offset",
                parameterSource,
                personDtoRowMapper);
    }

    @Override
    Optional<PersonDTO> reload(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId());
        return jdbcTemplate.queryForStream(
                "SELECT id, first_name, last_name, email, phone, date_of_birth, occupation, important " +
                        "FROM person WHERE id = :id",
                parameterSource,
                personDtoRowMapper).findFirst();
    }

    @Override
    void update(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId())
                .addValue("firstName", person.getFirstName())
                .addValue("lastName", person.getLastName())
                .addValue("email", person.getEmail())
                .addValue("phone", person.getPhone())
                .addValue("dateOfBirth", person.getDateOfBirth())
                .addValue("occupation", person.getOccupation())
                .addValue("important", person.isImportant());
        jdbcTemplate.update(
                "UPDATE person " +
                    "SET first_name = :firstName, last_name = :lastName, email = :email, phone = :phone, " +
                    "date_of_birth = :dateOfBirth, occupation = :occupation, important = :important " +
                    "WHERE id = :id", parameterSource);
    }

    @Override
    PersonDTO instantiateEmpty() {
        return new PersonDTO();
    }

    @Override
    boolean isImportant(PersonDTO person) {
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
            personDTO.setDateOfBirth(rs.getDate(6).toLocalDate());
            personDTO.setOccupation(rs.getString(7));
            personDTO.setImportant(rs.getBoolean(8));
            return personDTO;
        }
    }
}
