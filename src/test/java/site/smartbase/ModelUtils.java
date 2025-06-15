package site.smartbase;

import site.smartbase.dto.UserRegistrationRequest;
import site.smartbase.dto.VacancyDto;
import site.smartbase.entity.*;
import site.smartbase.enums.Source;
import site.smartbase.enums.UserRole;
import site.smartbase.enums.VacancyStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelUtils {

    public static List<Technology> getListOfTechnologies() {
        Technology technology1 = new Technology();
        technology1.setName("Java");
        Technology technology2 = new Technology();
        technology2.setName("React");
        Technology technology3 = new Technology();
        technology3.setName("Python");
        return new ArrayList<>(Arrays.asList(technology1, technology2, technology3));
    }

    public static List<Candidate> getListOfCandidates() {
        Candidate candidate1 = new Candidate();
        candidate1.setFirstName("John");
        candidate1.setLastName("Smith");
        Candidate candidate2 = new Candidate();
        candidate2.setFirstName("Jane");
        candidate2.setLastName("Doe");
        Candidate candidate3 = new Candidate();
        candidate3.setFirstName("Jack");
        candidate3.setLastName("Jones");
        return new ArrayList<>(Arrays.asList(candidate1, candidate2, candidate3));
    }

    public static List<Candidate> getListOfCandidatesWithTechnologies(List<Technology> technologies) {
        Candidate john = new Candidate();
        john.setFirstName("John");
        john.setLastName("Smith");
        john.setRegistrationDate(LocalDate.of(2025, 3, 13));
        john.setSource(Source.DJINNI);
        john.setTechnologies(List.of(technologies.get(0), technologies.get(1)));

        Candidate jane = new Candidate();
        jane.setFirstName("Jane");
        jane.setLastName("Doe");
        jane.setRegistrationDate(LocalDate.of(2025, 3, 14));
        jane.setSource(Source.LINKEDIN);
        jane.setTechnologies(List.of(technologies.get(0)));

        Candidate jack = new Candidate();
        jack.setFirstName("Jack");
        jack.setLastName("Jones");
        jack.setRegistrationDate(LocalDate.of(2025, 5, 22));
        jack.setSource(Source.LINKEDIN);
        jack.setTechnologies(technologies);

        return List.of(john, jane, jack);
    }

    public static List<Company> getCompanies() {
        Company company1 = new Company();
        company1.setName("Smart Base");
        company1.setRegistrationDate(LocalDate.of(2025, 3, 12));
        Company company2 = new Company();
        company2.setName("Company2");
        company2.setRegistrationDate(LocalDate.of(2025, 5, 7));
        return new ArrayList<>(Arrays.asList(company1, company2));
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .imagePath("/avatars/john.png")
                .registrationDate(LocalDate.of(2025, 3, 14))
                .build();
    }

    public static Candidate getCandidate() {
        return Candidate.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2025, 5, 22))
                .source(Source.LINKEDIN)
                .build();
    }

    public static Comment getComment() {
        return Comment.builder()
                .id(10L)
                .description("Great candidate!")
                .date(LocalDate.of(2024, 6, 1))
                .author(getUser())
                .addressee(getCandidate())
                .build();
    }

    public static Client getClient() {
        return Client.builder()
                .id(3L)
                .company(getCompanies().getFirst())
                .name("Client")
                .registrationDate(LocalDate.of(2024, 6, 1))
                .vacancies(List.of(getVacancy()))
                .build();
    }

    public static Vacancy getVacancy() {
        return Vacancy.builder()
                .status(VacancyStatus.IN_PROGRESS)
                .name("Java Developer")
                .description("We are seeking the best Java Developer")
                .id(9L)
                .creationDate(LocalDate.of(2024, 6, 1))
                .company(getCompanies().getFirst())
                .build();
    }

    public static VacancyDto getVacancyDto() {
        Vacancy vacancy = getVacancy();
        return VacancyDto.builder()
                .id(vacancy.getId())
                .creationDate(vacancy.getCreationDate())
                .description(vacancy.getDescription())
                .name(vacancy.getName())
                .status(vacancy.getStatus())
                .build();
    }

    public static UserRegistrationRequest getUserRegistrationRequest() {
        return UserRegistrationRequest.builder()
                .email("test@gmail.com")
                .firstName("Sasha")
                .lastName("Bulhakova")
                .role(String.valueOf(UserRole.RECRUITER))
                .build();
    }
}
