package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import java.io.Serializable;

public class ImagePojo implements Serializable {

    private byte[] image;

    public ImagePojo() {
    }
    public ImagePojo(byte[] image) {
        super();
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return (image==null ? 0:image.length) + " bytes";
    }

}
