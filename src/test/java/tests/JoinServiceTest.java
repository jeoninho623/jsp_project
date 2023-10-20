package tests;

import commons.BadRequestException;
import commons.Validator;
import models.member.JoinService;
import models.member.JoinValidator;
import models.member.Member;
import models.member.MemberDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원가입 기능 단위 테스트")
public class JoinServiceTest {

    private JoinService joinService;

    @BeforeEach
    void init() {
        joinService = new JoinService(new JoinValidator(), new MemberDao());
    }

    private Member getMember() {
        return Member.builder()
                .userId("user" + System.currentTimeMillis())
                .userPw("12345678")
                .confirmUserPw("12345678")
                .userNm("사용자")
                .email("user@test.org")
                .agree(true)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공시 예외발생하지 않음")
    void joinSuccess() {
        assertDoesNotThrow(() -> {
            joinService.join(getMember());
        });
    }

    @Test
    @DisplayName("필수 항목 검증(아이디, 비밀번호, 비밀번호 확인, 회원명, 이메일, 회원가입약관 동의), 검증 실패 시 예외 발생 BadRequestException")
    void requiredFieldCheck() {
        // 아이디(userId) 검증 null 또는 빈값("")
        assertThrows(BadRequestException.class, () -> {
            Member member = getMember();
            member.setUserId(null);
            joinService.join(member);

            member.setUserId("  ");
            joinService.join(member);
        });
    }
}
