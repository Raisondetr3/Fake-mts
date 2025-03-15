package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.fake_mts.dto.AddCardRequest;
import ru.itmo.fake_mts.dto.PaymentCardDto;
import ru.itmo.fake_mts.entity.PaymentCard;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.exception.InvalidCardDataException;
import ru.itmo.fake_mts.exception.TariffNotFoundException;
import ru.itmo.fake_mts.repo.PaymentCardRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardService {

    private final PaymentCardRepository cardRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public PaymentCardDto addCard(AddCardRequest request) {
        User user = currentUserService.getCurrentUserOrThrow();
        validateCardData(request);

        PaymentCard card = new PaymentCard();
        card.setUser(user);
        card.setPanMasked(maskPan(request.getPan()));
        card.setExpiryMonth(request.getExpiryMonth());
        card.setExpiryYear(request.getExpiryYear());
        card.setCardHolderName(request.getCardHolderName());

        PaymentCard saved = cardRepository.save(card);
        return PaymentCardDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentCardDto> getUserCards() {
        User user = currentUserService.getCurrentUserOrThrow();
        List<PaymentCard> cards = cardRepository.findByUserId(user.getId());
        return cards.stream()
                .map(PaymentCardDto::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteCard(Long cardId) {
        User user = currentUserService.getCurrentUserOrThrow();
        PaymentCard card = cardRepository.findById(cardId)
                .orElseThrow(() -> new TariffNotFoundException("Card not found"));
        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("The card belongs to another user");
        }
        cardRepository.delete(card);
    }

    private void validateCardData(AddCardRequest request) {
        if (!request.getPan().matches("\\d{16}")) {
            throw new InvalidCardDataException("The card number must consist of 16 digits");
        }
        if (request.getExpiryMonth() == null
                || request.getExpiryMonth() < 1
                || request.getExpiryMonth() > 12) {
            throw new InvalidCardDataException("Invalid month (1..12)");
        }
        if (request.getExpiryYear() == null
                || request.getExpiryYear() < LocalDate.now().getYear()) {
            throw new InvalidCardDataException("The year cannot be in the past");
        }
    }

    private String maskPan(String pan) {
        if (pan == null || pan.length() < 4) {
            return "****";
        }
        String last4 = pan.substring(pan.length() - 4);
        return "**** **** **** " + last4;
    }
}
