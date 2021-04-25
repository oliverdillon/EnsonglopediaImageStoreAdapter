package com.ensonglodpedia.adapters.EnsonglopediaImageStoreAdapter.Processes;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ImageAdapter extends XmlAdapter<String,ImagePojo> {
    @Override
    public ImagePojo unmarshal(String v) throws Exception {
        if(null==v) return null;
        return new ImagePojo(javax.xml.bind.DatatypeConverter.parseBase64Binary(v));
    }

    @Override
    public String marshal(ImagePojo v) throws Exception {
        if(null==v.getImage()) return null;
        return javax.xml.bind.DatatypeConverter.printBase64Binary(v.getImage());
    }
}
