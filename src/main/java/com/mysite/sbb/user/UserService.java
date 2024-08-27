package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.DataNotFoundException;
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
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
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
