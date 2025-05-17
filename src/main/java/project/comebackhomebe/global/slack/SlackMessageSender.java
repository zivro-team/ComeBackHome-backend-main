package project.comebackhomebe.global.slack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SlackMessageSender {

    @Value("${slack.webhook-uri}")
    private String webhookUri;

    @Value("${slack.channel}")
    private String channel;

    @Value("${slack.username}")
    private String username;

    @Value("${slack.emoji}")
    private String emoji;
}
