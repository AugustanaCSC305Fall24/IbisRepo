package edu.augustana;

public class HamRadio {

    public  double filterRange;
    public  double tuningFrequency;
    public double minFrequency;
    public double maxFrequency;

    public  double getTuningFrequency() {
        return tuningFrequency;
    }

    public  void setTuningFrequency(double tuningFrequency) {
        this.tuningFrequency = tuningFrequency;
    }

    // TODO: refactor other stuff so that this class is the ONLY one in charge
    // of keeping track of this data about the radio tuning/filtering


    public  double getFilterRange() {
        return filterRange;
    }

    public  void setFilterRange(double filterRange) {
        this.filterRange = filterRange;
    }

    void updateFilterRange(double currentFrequency, double currFilter) {
        minFrequency = Math.max(7.000, currentFrequency - currFilter / 2);
        maxFrequency = Math.min(7.067, currentFrequency + currFilter / 2);
    }

}
