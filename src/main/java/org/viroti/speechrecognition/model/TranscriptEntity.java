package org.viroti.speechrecognition.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transcripts")
public class TranscriptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uri;

    @Column(columnDefinition = "TEXT")
    private String transcript;

    private boolean piiData;

    private Instant createTime;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }

    public boolean isPiiData() { return piiData; }
    public void setPiiData(boolean piiData) { this.piiData = piiData; }

    public Instant getCreateTime() { return createTime; }
    public void setCreateTime(Instant createTime) { this.createTime = createTime; }
}

