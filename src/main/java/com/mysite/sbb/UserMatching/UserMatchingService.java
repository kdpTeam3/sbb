package com.mysite.sbb.UserMatching;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMatchingService {

    @Autowired
    private UserMatchingRepository repository;

    public List<UserMatching> findMatches(UserMatching targetUser, int numMatches) {
        List<UserMatching> allUsers = repository.findAll();
        return allUsers.stream()
                .filter(user -> !user.getId().equals(targetUser.getId()))
                .sorted((u1, u2) -> Double.compare(calculateSimilarity(targetUser, u1), calculateSimilarity(targetUser, u2)))
                .limit(numMatches)
                .collect(Collectors.toList());
    }

    private double calculateSimilarity(UserMatching user1, UserMatching user2) {
        double heightDiff = Math.abs(user1.getHeight() - user2.getHeight()) / 50;
        double weightDiff = Math.abs(user1.getWeight() - user2.getWeight()) / 30;
        double strengthDiff = Math.abs(user1.getIPFGLPoints() - user2.getIPFGLPoints()) / 500;

        double genderPenalty = user1.getGender().equals(user2.getGender()) ? 0 : 0.5;

        return heightDiff * 0.05 + weightDiff * 0.15 + strengthDiff * 0.5 + genderPenalty * 0.2;
    }
}
