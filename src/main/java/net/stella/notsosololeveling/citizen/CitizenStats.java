package net.stella.notsosololeveling.citizen;

public record CitizenStats (
        int strength,
        int agility,
        int perception,
        int vitality,
        int intelligence
){
    public double average()
    {
        return (
                strength
                + agility
                + perception
                + vitality
                + intelligence
                ) / 5.0;
    }
}
