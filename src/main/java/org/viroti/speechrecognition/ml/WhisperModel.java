package org.viroti.speechrecognition.ml;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.audio.translator.WhisperTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.modality.audio.Audio;
import ai.djl.modality.audio.AudioFactory;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.repository.zoo.Criteria;
import ai.djl.translate.TranslateException;
import org.bytedeco.ffmpeg.global.avutil;
import ai.djl.modality.audio.translator.SpeechRecognitionTranslatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.viroti.speechrecognition.exception.SpeechToTextException;

import java.io.IOException;
import java.nio.file.Path;
public class WhisperModel implements AutoCloseable{
    private final static Logger logger = LoggerFactory.getLogger(WhisperModel.class);
    private static ZooModel<Audio,String> model;

    public WhisperModel() throws ModelException,IOException {
        String modelPath = "/Users/gopalakrishnaviroti/Downloads/whisper_models";
        Criteria<Audio, String> criteria =
                Criteria.builder()
                        .setTypes(Audio.class, String.class)
                        .optModelUrls("djl://ai.djl.huggingface.pytorch/openai/whisper-small")
                        //.optModelUrls(modelPath)
                        .optEngine("PyTorch")
                        .optDevice(Device.cpu())
                        .optTranslatorFactory(new WhisperTranslatorFactory())
                        .build();
        model = criteria.loadModel();
        logger.info("Model loaded::::::::::");

    }

    public String speechToText(Audio speech) throws TranslateException{
        try(Predictor<Audio,String> predictor = model.newPredictor()){
            return predictor.predict(speech);
        }
    }

    public String speechToText(Path file) throws IOException,TranslateException{
        Audio audio = AudioFactory.newInstance()
                .setChannels(1)
                .setSampleRate(16000)
                .setSampleFormat(avutil.AV_SAMPLE_FMT_S16)
                .fromFile(file);
        return speechToText(audio);
    }


    @Override
    public void close(){
        model.close();
    }
}
