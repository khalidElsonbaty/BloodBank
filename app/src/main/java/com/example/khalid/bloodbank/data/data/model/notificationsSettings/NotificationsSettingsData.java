
package com.example.khalid.bloodbank.data.data.model.notificationsSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsSettingsData {

    @SerializedName("governorates")
    @Expose
    private List<String> governorates = null;
    @SerializedName("blood_types")
    @Expose
    private List<String> bloodTypes = null;

    public List<String> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(List<String> governorates) {
        this.governorates = governorates;
    }

    public List<String> getBloodTypes() {
        return bloodTypes;
    }

    public void setBloodTypes(List<String> bloodTypes) {
        this.bloodTypes = bloodTypes;
    }

}
