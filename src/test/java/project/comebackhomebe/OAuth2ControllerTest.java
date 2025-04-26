package project.comebackhomebe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;  // DogService 모의
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class OAuth2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private DogService dogService;  // 여기에 추가

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 카카오_로그인_JSON_받고_토큰_생성되는지_테스트() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("nickname", "홍길동");

        Map<String, Object> kakaoAccount = new HashMap<>();
        kakaoAccount.put("email", "hong@test.com");

        Map<String, Object> kakaoJson = new HashMap<>();
        kakaoJson.put("id", 1234567890L);
        kakaoJson.put("properties", properties);
        kakaoJson.put("kakao_account", kakaoAccount);

        String requestBody = objectMapper.writeValueAsString(kakaoJson);

        OAuth2Response fakeResponse = new OAuth2Response() {
            public String getProvider() { return "kakao"; }
            public String getProviderId() { return "1234567890"; }
            public String getEmail() { return "hong@test.com"; }
            public String getName() { return "홍길동"; }
        };

        when(memberService.getOAuth2Data(eq("kakao"), any(), any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/auth/oauth2/kakao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.provider").value("kakao"))
                .andExpect(jsonPath("$.providerId").value("1234567890"))
                .andExpect(jsonPath("$.email").value("hong@test.com"))
                .andExpect(jsonPath("$.name").value("홍길동"));
    }
}
