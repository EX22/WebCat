package by.khomenko.nsp.webcat.common.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer identityVal) {
        this.id = identityVal;
    }

    @Override
    public boolean equals(final Object object) {
        if (object != null) {
            if (object != this) {
                if (object.getClass() == getClass() && id != null) {
                    return id.equals(((Entity) object).id);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
