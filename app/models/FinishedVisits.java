package models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Entity(value = "finishedVisits", noClassnameStored = true)
public class FinishedVisits {

    @Id
    private ObjectId id;
    private String userId;
    private String clubId;
    private Long visitStart;
    private Long visitEnd;
    private Long qrScanned;
    private Long rating;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    
    public void setId(ObjectId id) {
            this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getClubId() {
        return clubId;
    }
    
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    
    public Long getVisitStart() {
        return visitStart;
    }
    
    public void setVisitStart(Long visitStart) {
        this.visitStart = visitStart;
    }
    
    public Long getVisitEnd() {
        return visitEnd;
    }
    
    public void setVisitEnd(Long visitEnd) {
        this.visitEnd = visitEnd;
    }
    
    public Long getQrScanned() {
        return qrScanned;
    }
    
    public void setQrScanned(Long qrScanned) {
        this.qrScanned = qrScanned;
    }
    
    public Long getRating() {
        return rating;
    }
    
    public void setRating(Long rating) {
        this.rating = rating;
    }
    
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    

    
}

