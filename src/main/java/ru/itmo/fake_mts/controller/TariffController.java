package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.TariffPresentation;
import ru.itmo.fake_mts.service.TariffService;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

    @GetMapping("/all")
    public List<TariffPresentation> getAll() {
        return tariffService.getAllTariffs();
    }

    @GetMapping("/{tariffId}")
    public TariffPresentation getTariff(@PathVariable Long tariffId) {
        return tariffService.getById(tariffId);
    }

    @PostMapping("/{userId}/activate-tariff")
    public String activateTariff(@PathVariable Long userId, @RequestParam Long tariffId) {
        return tariffService.activateTariff(userId, tariffId);
    }
}
