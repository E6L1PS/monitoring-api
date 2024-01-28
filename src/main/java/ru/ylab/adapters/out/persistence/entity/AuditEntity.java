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

    private String info;
    private String username;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "[DateTime: " + dateTime +
                "; Username: " + username +
                "; Info: " + info + ']';
    }
}
