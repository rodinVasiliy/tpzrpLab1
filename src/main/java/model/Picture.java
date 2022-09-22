package model;

public class Picture implements IEntity{

    private byte[] imageBytes;

    public Picture(byte[] bufferedImage){
        imageBytes = bufferedImage;
    }

    public byte[] getImageBytes(){
        return imageBytes;
    }

}
