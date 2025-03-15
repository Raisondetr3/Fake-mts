package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.TariffPresentation;
import ru.itmo.fake_mts.service.TariffService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

//    @GetMapping
//    public List<TariffPresentation> getAll() {
//        return tariffService.getAllTariffs();
//    }

    @GetMapping
    public List<TariffPresentation> getByConditions(
            @RequestParam(required = false) Integer gigabyteCount,
            @RequestParam(required = false) Integer minutesCount,
            @RequestParam(required = false) Integer smsCount
    ) {
        return tariffService.getByConditions(gigabyteCount, minutesCount, smsCount);
    }

    @GetMapping("/{tariffId}")
    public TariffPresentation getTariff(@PathVariable Long tariffId) {
        return tariffService.getById(tariffId);
    }

    @RequestMapping(value = "/{tariffId}/activate-tariff", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map activateTariff(@PathVariable Long tariffId) {
        return tariffService.activateTariff(tariffId);
    }
}
