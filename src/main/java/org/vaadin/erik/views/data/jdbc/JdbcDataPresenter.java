package org.vaadin.erik.views.data.jdbc;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.dto.PhoneDTO;
import org.vaadin.erik.views.data.DataPresenter;

import javax.persistence.OptimisticLockException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JdbcDataPresenter implements DataPresenter<PersonDTO, PhoneDTO> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PersonDTORowMapper personDtoRowMapper = new PersonDTORowMapper();
    private final PhoneDTORowMapper phoneDTORowMapper = new PhoneDTORowMapper();

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
        List<PersonDTO> persons = jdbcTemplate.query(
                String.format("SELECT id, first_name, last_name, email, date_of_birth, occupation, important, version " +
                        "FROM person %s LIMIT :limit OFFSET :offset", buildSortString(query)),
                parameterSource,
                personDtoRowMapper);

        parameterSource = new MapSqlParameterSource()
                .addValue("personIds", persons.stream().map(PersonDTO::getId).collect(Collectors.toList()));
        List<PhoneDTO> phones = jdbcTemplate.query("SELECT id, phone, person_id FROM phone WHERE person_id IN (:personIds)",
                parameterSource,
                phoneDTORowMapper);

        return persons.stream().peek(person -> person.setPhones(
                phones.stream()
                        .filter(phone -> phone.getPersonId().equals(person.getId()))
                        .collect(Collectors.toList()))
        );
    }

    @Override
    public Optional<PersonDTO> reload(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId());
        Optional<PersonDTO> personDTO = jdbcTemplate.queryForStream(
                "SELECT id, first_name, last_name, email, date_of_birth, occupation, important, version " +
                        "FROM person WHERE id = :id",
                parameterSource,
                personDtoRowMapper).findFirst();

        return personDTO.map(p -> {
            List<PhoneDTO> phones = jdbcTemplate.query("SELECT id, phone, person_id FROM phone WHERE person_id = :id",
                    parameterSource,
                    phoneDTORowMapper);
            p.setPhones(phones);
            return p;
        });
    }

    @Override
    public void updateOrInsert(PersonDTO person) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", person.getId())
                .addValue("firstName", person.getFirstName())
                .addValue("lastName", person.getLastName())
                .addValue("email", person.getEmail())
                .addValue("dateOfBirth", person.getDateOfBirth())
                .addValue("occupation", person.getOccupation())
                .addValue("important", person.isImportant())
                .addValue("version", person.getVersion());
        if (person.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO person (first_name, last_name, email, date_of_birth, occupation, important) " +
                            "VALUES (:firstName, :lastName, :email, :dateOfBirth, :occupation, :important)",
                    parameterSource);
        } else {
            int rowsAffected = jdbcTemplate.update(
                    "UPDATE person " +
                            "SET first_name = :firstName, last_name = :lastName, email = :email, " +
                            "date_of_birth = :dateOfBirth, occupation = :occupation, important = :important, " +
                            "version = version + 1 " +
                            "WHERE id = :id AND version = :version", parameterSource);
            if (rowsAffected == 0) {
                // Technically it could also be removed, but I think this is good enough
                throw new OptimisticLockException();
            }
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

    @Override
    public List<PhoneDTO> getPhones(PersonDTO person) {
        return person.getPhones();
    }

    @Override
    public void setPhones(PersonDTO personDTO, List<PhoneDTO> phoneDTOS) {
        personDTO.setPhones(phoneDTOS);
    }

    @Override
    public PhoneDTO createPhone(String phone) {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPhone(phone);
        return phoneDTO;
    }

    @Override
    public String getPhoneString(PhoneDTO phoneDTO) {
        return phoneDTO.getPhone();
    }

    static class PersonDTORowMapper implements RowMapper<PersonDTO> {

        @Override
        public PersonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(rs.getInt(1));
            personDTO.setFirstName(rs.getString(2));
            personDTO.setLastName(rs.getString(3));
            personDTO.setEmail(rs.getString(4));
            personDTO.setDateOfBirth(Optional.ofNullable(rs.getDate(5)).map(Date::toLocalDate).orElse(null));
            personDTO.setOccupation(rs.getString(6));
            personDTO.setImportant(rs.getBoolean(7));
            personDTO.setVersion(rs.getInt(8));
            return personDTO;
        }
    }

    static class PhoneDTORowMapper implements RowMapper<PhoneDTO> {

        @Override
        public PhoneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setId(rs.getInt(1));
            phoneDTO.setPhone(rs.getString(2));
            phoneDTO.setPersonId(rs.getInt(3));
            return phoneDTO;
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
