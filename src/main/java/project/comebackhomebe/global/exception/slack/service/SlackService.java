package project.comebackhomebe.global.exception.slack.service;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.webhook.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.comebackhomebe.global.exception.slack.data.Color;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SlackService {
    @Value("${slack.webhook-uri}")
    private String webhookUri;

    private final Slack slackClient = Slack.getInstance();

    /**
     * 슬랙에 메세지 전송
     */
    public void sendMessageToSlack(String title, Map<String, String> data) {
        try {
            slackClient.send(webhookUri, Payload.builder()
                    .text(title)
                    .attachments(List.of(
                            Attachment.builder()
                                    .color(Color.RED.getColor())
                                    .fields(data.keySet()
                                                    .stream()
                                                    .map(key -> generateSlackField(key, data.get(key)))
                                                    .collect(Collectors.toList()))
                                    .build()))
                    .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Slack Field 생성
     **/
    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }


}
