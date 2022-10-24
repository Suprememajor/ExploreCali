package com.supremecorp.explorecali.domain;

public enum Region {
    SOUTH_WEST("South West"), NORTH_WEST("North West"),
    LITTORAL("Littoral"), CENTER("Center");
    private String label;
    private Region(String label) {
        this.label = label;
    }
    public static Region findByLabel(String label) {
        for (Region region: Region.values()) {
            if (region.label.equalsIgnoreCase(label))
                return region;
        }
        return null;
    }
}
