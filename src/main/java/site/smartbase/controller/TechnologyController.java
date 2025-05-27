package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.smartbase.dto.TechnologyDto;
import site.smartbase.service.TechnologyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/technologies")
public class TechnologyController {
    private final TechnologyService technologyService;

    @GetMapping
    public ResponseEntity<List<TechnologyDto>> getAllTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnologies());
    }
}
