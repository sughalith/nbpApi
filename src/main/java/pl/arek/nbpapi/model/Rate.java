package pl.arek.nbpapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rate {

    @SerializedName("no")
    @Expose
    private String no;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("mid")
    @Expose
    private Double mid;

}
