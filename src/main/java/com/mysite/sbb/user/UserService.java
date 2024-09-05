package com.mysite.sbb.user;

import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    // findByEmail을 UserRepository에서 호출하도록 수정
    public Optional<SiteUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public SiteUser create(String username, String email, String password) {
        // 이메일 중복 체크
        Optional<SiteUser> existingUser = userRepository.findByEmail(email); // findByEmail 사용 
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        return siteUser.orElse(null);  // 사용자 정보를 찾지 못하면 null을 반환
    }

    public SiteUser getUserById(String userId) {
        try {
            Long id = Long.parseLong(userId);
            Optional<SiteUser> user = userRepository.findById(id);
            return user.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + userId);
        }
    }

    public void modify(SiteUser siteUser, String username, String email) {
        // 이메일 중복 체크
        Optional<SiteUser> existingUser = userRepository.findByEmail(email); // UserRepository의 findByEmail 사용
        if (existingUser.isPresent() && !existingUser.get().getUsername().equals(siteUser.getUsername())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        siteUser.setUsername(username);
        siteUser.setEmail(email);
        this.userRepository.save(siteUser);
    }

    @Transactional
    public void delete(SiteUser siteUser) {
        // 사용자가 작성한 질문 삭제
        questionRepository.deleteByAuthor(siteUser);
        // 사용자가 작성한 답변 삭제
        answerRepository.deleteByAuthor(siteUser);
        // 최종적으로 사용자 삭제
        this.userRepository.delete(siteUser);
    }
}
