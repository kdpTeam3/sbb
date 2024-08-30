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

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if(siteUser.isPresent()) {
            System.out.println("User found: " + siteUser.get().getUsername());
        } else {
            System.out.println("User not found with username: " + username);
        }
        return siteUser.orElse(null);  // 사용자 정보를 찾지 못하면 null을 반환
    }

    public SiteUser getUserById(String userId) {
        try {
            // String을 Long으로 변환
            Long id = Long.parseLong(userId);
            Optional<SiteUser> user = userRepository.findById(id);
            return user.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + userId);
        }
    }

    public void modify(SiteUser siteUser, String username, String email) {
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
