package ru.ylab.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntity {

    private Long id;
    private Long userId;
    private String info;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "[DateTime: " + dateTime +
               "; UserId: " + userId +
               "; Info: " + info + ']';
    }
}
