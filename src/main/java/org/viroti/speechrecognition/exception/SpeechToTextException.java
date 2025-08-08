package org.viroti.speechrecognition.exception;

public class SpeechToTextException extends Exception{
    public SpeechToTextException(String message) {
        super(message);
    }
    public SpeechToTextException(String message,Throwable cause){
        super(message,cause);
    }
}
