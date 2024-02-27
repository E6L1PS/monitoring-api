package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.in.AuditService;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.Audit;
import ru.ylab.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional
@Service("AuditServiceImpl")
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    private final AuditMapper auditMapper;

    @Auditable
    @Override
    public List<AuditDto> getAll() {
        List<AuditEntity> auditEntities = auditRepository.findAll();
        List<Audit> audits = auditMapper.toListDomain(auditEntities);
        //internal business logic with domain model if needed
        List<AuditDto> auditsDto = auditMapper.toListDto(audits);
        return auditsDto;
    }

    @Override
    public void save(String signature) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;

        if (principal instanceof User user) {
            userId = user.getId();
        }

        auditRepository.save(AuditEntity.builder()
                .info(generateInfoMessage(signature))
                .dateTime(LocalDateTime.now())
                .userId(userId)
                .build());

    }

    private String generateInfoMessage(String signature) {
        return switch (signature) {
            case "MeterTypeServiceImpl.save" -> "Добавлен новый тип счетчика!";
            case "MeterServiceImpl.getAll", "MeterServiceImpl.getAllById" -> "Просмотр всех показаний!";
            case "AuditServiceImpl.getAll" -> "Просмотр всех аудитов!";
            case "MeterServiceImpl.getLastById" -> "Просмотр последних показаний!";
            case "MeterServiceImpl.getByMonth" -> "Просмотр всех показаний за конкретный месяц!";
            case "MeterTypeServiceImpl.getAll" -> "Просмотр всех типов показаний!";
            case "UserServiceImpl.authenticate" -> "Авторизация выполнена!";
            case "UserServiceImpl.save" -> "Новый пользователь зарегистрирован!";
            case "MeterServiceImpl.save" -> "Показания поданы!";
            default -> "Действие: " + signature;
        };
    }

}
