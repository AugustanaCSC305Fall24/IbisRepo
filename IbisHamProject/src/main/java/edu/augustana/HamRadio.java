package edu.augustana;

public class HamRadio {

    public  double tuningFrequency;

    public  double getTuningFrequency() {
        return tuningFrequency;
    }

    public  void setTuningFrequency(double tuningFrequency) {
        this.tuningFrequency = tuningFrequency;
    }

    // TODO: refactor other stuff so that this class is the ONLY one in charge
    // of keeping track of this data about the radio tuning/filtering

}
