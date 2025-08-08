package org.viroti.speechrecognition.service;

import ai.djl.ModelException;
import ai.djl.modality.audio.Audio;
import ai.djl.modality.audio.AudioFactory;
import ai.djl.translate.TranslateException;
import org.springframework.stereotype.Service;
import org.viroti.speechrecognition.exception.SpeechToTextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.viroti.speechrecognition.ml.WhisperModel;
import org.viroti.speechrecognition.model.TranscriptEntity;
import org.viroti.speechrecognition.repo.TranscriptRepository;
import org.viroti.speechrecognition.request.FileReqPayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpeechToTextService {
    private static final Logger logger = LoggerFactory.getLogger(SpeechToTextService.class);
    private final TranscriptRepository transcriptRepository;

    public SpeechToTextService(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    public String getTranscriptFromFile(final FileReqPayload fileReqPayload) throws SpeechToTextException {
        List<Map<String,Object>> rowDataList = new ArrayList<>();
        Audio audio = null;
        String audioFilePath = fileReqPayload.getFilePath();
        Path path =null;
        boolean isUrlFile = false;
        if (audioFilePath.startsWith("http://") || audioFilePath.startsWith("https://")) {
            isUrlFile =true;
        }
        else {
            path = Paths.get(audioFilePath);
            if (!Files.exists(path)) {
                throw new SpeechToTextException("File not found : " + audioFilePath);
            }
        }
        try (WhisperModel model = new WhisperModel()) {
            String rawTranscript = "";
            if(isUrlFile)
                rawTranscript= model.speechToText(AudioFactory.newInstance().fromUrl(audioFilePath));
            else
                rawTranscript=model.speechToText(path);
            String transcript = cleanTranscript(rawTranscript);

            TranscriptEntity entity = new TranscriptEntity();
            entity.setUri(audioFilePath);
            entity.setTranscript(transcript);
            entity.setPiiData(false);
            entity.setCreateTime(Instant.now());

            transcriptRepository.save(entity);

            return transcript;
        } catch (IOException | ModelException | TranslateException  e) {
            logger.error("Exception From getTranscriptFromFile :: "+e.getMessage());
            throw new SpeechToTextException("Failed to process file : " + audioFilePath, e);
        }

    }

    private String cleanTranscript(String rawText){
        return rawText.replaceAll("<\\|.*?\\|>","");
    }
}
