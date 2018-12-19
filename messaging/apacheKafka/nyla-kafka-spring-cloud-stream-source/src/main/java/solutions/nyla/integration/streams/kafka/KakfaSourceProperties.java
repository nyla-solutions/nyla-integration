package solutions.nyla.integration.streams.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("kakfa")
@Validated
public class KakfaSourceProperties
{

}
