package com.example.MC2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class MessageDTO
{
    @JsonProperty("session_id")
    private Integer sessionId;

    @JsonProperty("MC1_timestamp")
    private Date MC1Timestamp;

    @JsonProperty("MC2_timestamp")
    private Date MC2Timestamp;

    @JsonProperty("MC3_timestamp")
    private Date MC3Timestamp;

    @JsonProperty("end_timestamp")
    private Date endTimestamp;

    public Integer getSessionId()
    {
        return sessionId;
    }

    public void setSessionId( Integer sessionId )
    {
        this.sessionId = sessionId;
    }

    public Date getMC1Timestamp()
    {
        return MC1Timestamp;
    }

    public void setMC1Timestamp( Date MC1Timestamp )
    {
        this.MC1Timestamp = MC1Timestamp;
    }

    public Date getMC2Timestamp()
    {
        return MC2Timestamp;
    }

    public void setMC2Timestamp( Date MC2Timestamp )
    {
        this.MC2Timestamp = MC2Timestamp;
    }

    public Date getMC3Timestamp()
    {
        return MC3Timestamp;
    }

    public void setMC3Timestamp( Date MC3Timestamp )
    {
        this.MC3Timestamp = MC3Timestamp;
    }

    public Date getEndTimestamp()
    {
        return endTimestamp;
    }

    public void setEndTimestamp( Date endTimestamp )
    {
        this.endTimestamp = endTimestamp;
    }
}
