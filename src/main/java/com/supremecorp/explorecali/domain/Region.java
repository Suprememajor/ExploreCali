package com.supremecorp.explorecali.domain;

public enum Region {
    Buea("Buea"), Douala("Douala"), Kumba("Kumba"), Limbe("Limbe");
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
