package ru.itmo.node_a_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itmo.common.dto.AddCardRequest;
import ru.itmo.common.dto.PaymentCardDto;
import ru.itmo.node_a_core.service.PaymentCardService;

import java.util.List;

@RestController
@RequestMapping("/api/users/cards")
@RequiredArgsConstructor
public class PaymentCardController {

    private final PaymentCardService cardService;

    @PostMapping
    public PaymentCardDto addCard(@RequestBody AddCardRequest request) {
        return cardService.addCard(request);
    }

    @GetMapping
    public List<PaymentCardDto> getCards() {
        return cardService.getUserCards();
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }
}

