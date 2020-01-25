package by.khomenko.nsp.webcat.common.entity;

public enum Role {

    DISTRIBUTOR("distributor"),
    CUSTOMER("customer");

    private String name;

    Role(final String nameVal) {
        this.name = nameVal;
    }

    public String getName() {
        return name;
    }

    public Integer getIdentity() {
        return ordinal();
    }

    public static Role getByIdentity(final Integer identity) {
        return Role.values()[identity];
    }
}
