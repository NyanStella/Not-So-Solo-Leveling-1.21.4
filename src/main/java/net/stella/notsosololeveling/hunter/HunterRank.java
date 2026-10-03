package net.stella.notsosololeveling.hunter;

public enum HunterRank {
    E("E", 0x9E9E9E, 16, 1.0),
    D("D", 0x55AA55, 40, 1.5),
    C("C", 0x5555FF, 30, 2.0),
    B("B", 0xAA00AA, 10, 3.0),
    A("A", 0xFF5555, 3, 4.0),
    S("S", 0xFFAA00, 1, 5.0);

    private final String displayName;
    private final int color;
    private final int rollWeight;
    private final double statMultiplier;

    HunterRank(
            String displayName,
            int color,
            int rollWeight,
            double statMultiplier){

        this.displayName = displayName;
        this.color = color;
        this.rollWeight = rollWeight;
        this.statMultiplier = statMultiplier;
    }

    public String getDisplayName(){
        return displayName;
    }

    public int getColor(){
        return color;
    }

    public int getRollWeight(){
        return rollWeight;
    }

    public double getStatMultiplier(){
        return statMultiplier;
    }
}
