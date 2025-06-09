package project.comebackhomebe.global.config.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "note") // 액추에이터에 엔드포인트로 자동으로 등록되며 id 속성값으로 경로를 정의할 수 있다.
public class NoteEndpoint {

    // 객체 생성
    private final Map<String, Object> noteContent = new HashMap<>();

    // HTTP GET 요청에 반응하는 메서드 생성
    @ReadOperation
    public Map<String, Object> getNote() {
        return noteContent;
    }

    // HTTP POST 요청에 반응하는 메서드 생성
    @WriteOperation
    public Map<String, Object> writeNote(String key, Object value) {
        noteContent.put(key, value);
        return noteContent;
    }

    // HTTP DELETE 요청에 반응하는 메서드 생성
    @DeleteOperation
    public Map<String, Object> deleteNote(String key) {
        noteContent.remove(key);
        return noteContent;
    }

}
