package Parking;

import java.util.Date;

class Photo {
    // photo metadata
    Byte[] Data;

    // photo creation date, i.e. time that photo was taken of car parking
    // instance
    Date creationDate;

    // hash of photo; unique identifier for each photo
    String photoHash;

    public Photo(Byte[] data, Date creationDate, String photoHash) {
        Data = data;
        this.creationDate = creationDate;
        this.photoHash = photoHash;
    }

    public Byte[] getData() {
        return Data;
    }

    public void setData(Byte[] data) {
        Data = data;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getphotoHash() {
        return photoHash;
    }

    public void setHash(String photoHash) {
        this.photoHash = photoHash;
    }

}
