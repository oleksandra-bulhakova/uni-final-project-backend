package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.ClientResponse;
import site.smartbase.dto.UserResponse;
import site.smartbase.dto.VacancyDto;
import site.smartbase.dto.VacancyEditDto;
import site.smartbase.entity.Client;
import site.smartbase.entity.Company;
import site.smartbase.entity.User;
import site.smartbase.entity.Vacancy;
import site.smartbase.enums.UserRole;
import site.smartbase.enums.VacancyStatus;
import site.smartbase.exception.NoAccessException;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ClientRepo;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.repository.VacancyRepo;
import site.smartbase.service.VacancyService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepo vacancyRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final ClientRepo clientRepo;

    @Transactional
    @Override
    public VacancyDto addVacancy(Long currentUserId, VacancyDto vacancyDto) {
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        Company company = companyRepo.findById(user.getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Client client = clientRepo.findById(vacancyDto.getClient().getId()).orElseThrow(() -> new NotFoundException("Client not found"));
        List<User> users = new ArrayList<>();
        users.add(user);

        Vacancy vacancy = modelMapper.map(vacancyDto, Vacancy.class);
        vacancy.setCompany(company);
        vacancy.setClient(client);
        vacancy.setUsers(users);

        vacancyRepo.save(vacancy);

        if (user.getVacancies() == null) {
            user.setVacancies(new ArrayList<>());
        }

        user.getVacancies().add(vacancy);

        List<User> userList = vacancy.getUsers();
        List<UserResponse> userResponses = new ArrayList<>();

        if (userList != null) {
            userResponses = userList.stream()
                    .map(userToMap -> modelMapper.map(userToMap, UserResponse.class)).toList();
        }

        VacancyDto createdVacancyDto = modelMapper.map(vacancy, VacancyDto.class);
        createdVacancyDto.setUsers(userResponses);

        ClientResponse clientResponse = modelMapper.map(client, ClientResponse.class);
        createdVacancyDto.setClient(clientResponse);

        return createdVacancyDto;
    }

    @Override
    public List<VacancyDto> getAllVacancies(Long currentUserId) {
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        Company company = companyRepo.findById(user.getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        List<Vacancy> vacancies = vacancyRepo.findAllByCompany_Id(company.getId());
        List<VacancyDto> vacancyDtos = new ArrayList<>();

        if (vacancies != null) {
            vacancyDtos = vacancies.stream().map(
                    vacancy -> modelMapper.map(vacancy, VacancyDto.class))
                    .toList();
        }
        return vacancyDtos;
    }

    @Override
    public List<VacancyDto> getAllVacanciesForUser(Long currentUserId) {
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));

        List<Vacancy> vacancies = vacancyRepo.findByUsers_Id(currentUserId);
        List<VacancyDto> vacancyDtos = new ArrayList<>();

        if (vacancies != null) {
            vacancyDtos = vacancies.stream().map(
                            vacancy -> modelMapper.map(vacancy, VacancyDto.class))
                    .toList();
        }
        return vacancyDtos;
    }

    @Override
    public VacancyDto getVacancy(Long vacancyId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));
        return modelMapper.map(vacancy, VacancyDto.class);
    }

    @Override
    public List<VacancyDto> getVacanciesForCandidate(Long candidateId) {
        List<Vacancy> vacancies = vacancyRepo.findByCandidates_Id(candidateId);
        List<VacancyDto> vacancyDtos = new ArrayList<>();
        if (vacancies != null) {
            vacancyDtos = vacancies.stream().map(vacancy -> modelMapper.map(vacancy, VacancyDto.class)).toList();
        }
        return vacancyDtos;
    }

    @Transactional
    @Override
    public VacancyDto changeVacancyStatus(Long vacancyId, String status) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));
        vacancy.setStatus(VacancyStatus.valueOf(status));
        return modelMapper.map(vacancy, VacancyDto.class);
    }

    @Transactional
    @Override
    public VacancyDto addRecruiterToVacancy(Long vacancyId, Long recruiterId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));
        User user = userRepo.findById(recruiterId).orElseThrow(() -> new NotFoundException("User not found"));

        if (!(user.getRole().equals(UserRole.RECRUITER) || user.getRole().equals(UserRole.OWNER))) {
            throw new NoAccessException("This user can't be assigned to the vacancy");
        }

        vacancy.getUsers().add(user);
        user.getVacancies().add(vacancy);

        return modelMapper.map(vacancy, VacancyDto.class);
    }

    @Transactional
    @Override
    public VacancyDto removeRecruiterFromVacancy(Long vacancyId, Long recruiterId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));
        User user = userRepo.findById(recruiterId).orElseThrow(() -> new NotFoundException("User not found"));

        vacancy.getUsers().remove(user);
        user.getVacancies().remove(vacancy);

        return modelMapper.map(vacancy, VacancyDto.class);
    }

    @Transactional
    @Override
    public VacancyDto updateVacancy(Long vacancyId, VacancyEditDto vacancyEditDto) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));

        vacancy.setName(vacancyEditDto.getName());
        vacancy.setDescription(vacancyEditDto.getDescription());

        return modelMapper.map(vacancy, VacancyDto.class);
    }

    @Transactional
    @Override
    public void deleteVacancy(Long vacancyId) {
        vacancyRepo.deleteById(vacancyId);
    }
}
