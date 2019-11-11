package Parking;

import java.util.Date;

class Photo {
    // photo metadata
    Byte[] data;

    // photo creation date, i.e. time that photo was taken of car parking instance
    Date creationDate;

    // hash of photo; unique identifier for each photo
    String photoHash;
    
    // path of photo file based on user input
    String photoFilePath;

    public Photo(Byte[] data, Date creationDate, String photoHash) {
        this.data = data;
        this.creationDate = creationDate;
        this.photoHash = photoHash;
    }

    public Byte[] getData() {
        return data;
    }

    public void setData(Byte[] data) {
        this.data = data;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhotoHash() {
        return photoHash;
    }
    
    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setHash(String photoHash) {
        this.photoHash = photoHash;
    }

}
