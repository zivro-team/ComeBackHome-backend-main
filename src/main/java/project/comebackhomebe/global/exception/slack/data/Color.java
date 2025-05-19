package project.comebackhomebe.global.exception.slack.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    RED("#ff0000");

    private final String color;
}
