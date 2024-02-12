package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.adapters.in.web.dto.AuditModel;
import ru.ylab.application.out.AuditRepository;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class GetAuditInfoImpl implements GetAuditInfo {

    @Autowired
    private AuditRepository auditRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditModel> execute() {
        return AuditMapper.INSTANCE.toListAuditModel(auditRepository.findAll());
    }
}
