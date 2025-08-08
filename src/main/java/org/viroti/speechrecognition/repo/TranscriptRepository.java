package org.viroti.speechrecognition.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viroti.speechrecognition.model.TranscriptEntity;

public interface TranscriptRepository extends JpaRepository<TranscriptEntity, Long> {
}
