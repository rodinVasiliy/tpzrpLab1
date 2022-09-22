package dto.request;

import model.IEntity;
import model.Picture;

import java.io.Serializable;

public class PictureSendRequest extends Request implements Serializable {

    private final IEntity picture;

    public PictureSendRequest(IEntity picture) {
        this.picture = picture;
        super.setEntityType(Picture.class);
    }

    public IEntity getPicture() {
        return picture;
    }
}
