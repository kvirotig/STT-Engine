package org.viroti.speechrecognition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viroti.speechrecognition.exception.SpeechToTextException;
import org.viroti.speechrecognition.request.FileReqPayload;
import org.viroti.speechrecognition.service.SpeechToTextService;

@RestController
@RequestMapping("/api/speech")
public class SpeechToTextController {

    @Autowired
    private SpeechToTextService speechToTextService;

    @PostMapping("/transcript")
    public ResponseEntity<String> getTranscript(@RequestBody final FileReqPayload fileReqPayload) throws SpeechToTextException{
        String transcript = speechToTextService.getTranscriptFromFile(fileReqPayload);
        return ResponseEntity.ok(transcript);
    }
}
