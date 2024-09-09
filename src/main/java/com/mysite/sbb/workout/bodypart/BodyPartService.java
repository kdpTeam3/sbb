package com.mysite.sbb.workout.bodypart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BodyPartService {
    private final BodyPartRepository bodyPartRepository;

    public BodyPart save(BodyPart bodyPart) {
        return bodyPartRepository.save(bodyPart);
    }


    public void update(Long itemId, BodyPartUpdateDto updateParam) {
        BodyPart findItem = bodyPartRepository.findById(itemId).orElseThrow();
        findItem.setChest(updateParam.getChest());
        findItem.setTriceps(updateParam.getTriceps());
        findItem.setBack(updateParam.getBack());
        findItem.setBiceps(updateParam.getBiceps());
        findItem.setLeg(updateParam.getLeg());
        findItem.setShoulder(updateParam.getShoulder());
        findItem.setAbs(updateParam.getAbs());
    }
}