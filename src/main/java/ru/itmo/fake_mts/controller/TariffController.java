package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.entity.Tariff;
import ru.itmo.fake_mts.service.TariffService;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

    @GetMapping("/all")
    public List<Tariff> getAll() {
        return tariffService.getAllTariffs();
    }

    @GetMapping("/{tariffId}")
    public Tariff getTariff(@PathVariable Long tariffId) {
        return tariffService.getById(tariffId);
    }

    @PostMapping("/{userId}/activate-tariff")
    public Boolean activateTariff(@PathVariable Long userId, @RequestParam Long tariffId) {
        return tariffService.activateTariff(userId, tariffId);
    }
}
