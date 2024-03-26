package de.dennisguse.opentracks.data.models;

public class CalorieCalculator {

    // Method to calculate calories burned during skiing
    public double calculateCalories(double weightInKg, double durationInHours, double metValue) {
        double caloriesBurned = metValue * weightInKg * durationInHours; //MET (Metabolic Equivalent of Task) value is a unit used to measure the intensity of physical activities.
        return caloriesBurned;
    }

    // Overloaded method with a default MET value
    public double calculateCalories(double weightInKg, double durationInHours) {
        // Default MET value for skiing
        double defaultMetValue = 5.0; // You can adjust this default value as needed

        // Call the main calculateCalories method with the default MET value
        return calculateCalories(weightInKg, durationInHours, defaultMetValue);
    }
}