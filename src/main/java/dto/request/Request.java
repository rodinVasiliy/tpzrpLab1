package dto.request;


import model.IEntity;

import java.io.Serializable;

public class Request {
    protected Class<? extends IEntity> entityType;

    public void setEntityType(Class<? extends IEntity> entityType) {
        this.entityType = entityType;
    }

    public Class<? extends IEntity> getEntityType() {
        return entityType;
    }
}
