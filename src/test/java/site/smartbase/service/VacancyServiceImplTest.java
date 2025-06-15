package site.smartbase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import site.smartbase.ModelUtils;
import site.smartbase.dto.VacancyDto;
import site.smartbase.entity.Client;
import site.smartbase.entity.Vacancy;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ClientRepo;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.VacancyRepo;
import site.smartbase.service.impl.VacancyServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacancyServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private VacancyRepo vacancyRepo;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Test
    void changeClientTest() {
        Client client = ModelUtils.getClient();
        Vacancy vacancy = ModelUtils.getVacancy();
        VacancyDto vacancyDto = ModelUtils.getVacancyDto();

        when(vacancyRepo.findById(vacancy.getId())).thenReturn(Optional.of(vacancy));
        when(clientRepo.findById(client.getId())).thenReturn(Optional.of(client));
        when(modelMapper.map(vacancy, VacancyDto.class)).thenReturn(vacancyDto);

        VacancyDto result = vacancyService.changeClient(vacancy.getId(), client.getId());

        assertEquals(vacancyDto, result);
        verify(vacancyRepo).findById(vacancy.getId());
        verify(clientRepo).findById(client.getId());
    }

    @Test
    void changeClient_ClientNotFoundTest() {
        Long clientId = 7L;
        Vacancy vacancy = ModelUtils.getVacancy();

        when(vacancyRepo.findById(vacancy.getId())).thenReturn(Optional.of(vacancy));
        when(clientRepo.findById(clientId)).thenThrow(new NotFoundException("Client not found"));

        assertThrows(NotFoundException.class, () -> vacancyService.changeClient(vacancy.getId(), clientId));

        verify(vacancyRepo).findById(vacancy.getId());
        verify(clientRepo).findById(clientId);
        verifyNoInteractions(modelMapper);
    }
}
