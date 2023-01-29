package study.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {

    private String remoteIp;
    private String sessiondId;
    private LocalDateTime loginTime;

}
