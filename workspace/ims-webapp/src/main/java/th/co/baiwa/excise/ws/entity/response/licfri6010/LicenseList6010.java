
package th.co.baiwa.excise.ws.entity.response.licfri6010;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LicenseList6010 {

    @SerializedName("Offcode")
    @Expose
    private String offcode;
    @SerializedName("LicType")
    @Expose
    private String licType;
    @SerializedName("LicCode")
    @Expose
    private String licCode;
    @SerializedName("LicNo")
    @Expose
    private String licNo;
    @SerializedName("LicName")
    @Expose
    private String licName;
    @SerializedName("LicFee")
    @Expose
    private String licFee;
    @SerializedName("LicInterior")
    @Expose
    private String licInterior;
    @SerializedName("LicPrice")
    @Expose
    private String licPrice;
    @SerializedName("LicDate")
    @Expose
    private String licDate;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("SendDate")
    @Expose
    private String sendDate;
    @SerializedName("PrintCount")
    @Expose
    private String printCount;
    @SerializedName("Nid")
    @Expose
    private String nid;
    @SerializedName("NewregId")
    @Expose
    private String newregId;
    @SerializedName("CusFullName")
    @Expose
    private String cusFullName;
    @SerializedName("FacFullName")
    @Expose
    private String facFullName;

    private List<String> year;
    
    public String getOffcode() {
        return offcode;
    }

    public void setOffcode(String offcode) {
        this.offcode = offcode;
    }

    public String getLicType() {
        return licType;
    }

    public void setLicType(String licType) {
        this.licType = licType;
    }

    public String getLicCode() {
        return licCode;
    }

    public void setLicCode(String licCode) {	
        this.licCode = licCode;
    }

    public String getLicName() {
        return licName;
    }

    public void setLicName(String licName) {
        this.licName = licName;
    }

    public String getLicFee() {
        return licFee;
    }

    public void setLicFee(String licFee) {
        this.licFee = licFee;
    }

    public String getLicInterior() {
        return licInterior;
    }

    public void setLicInterior(String licInterior) {
        this.licInterior = licInterior;
    }

    public String getLicPrice() {
        return licPrice;
    }

    public void setLicPrice(String licPrice) {
        this.licPrice = licPrice;
    }

    public String getLicDate() {
        return licDate;
    }

    public void setLicDate(String licDate) {
        this.licDate = licDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getPrintCount() {
        return printCount;
    }

    public void setPrintCount(String printCount) {
        this.printCount = printCount;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNewregId() {
        return newregId;
    }

    public void setNewregId(String newregId) {
        this.newregId = newregId;
    }

    public String getCusFullName() {
        return cusFullName;
    }

    public void setCusFullName(String cusFullName) {
        this.cusFullName = cusFullName;
    }

    public String getFacFullName() {
        return facFullName;
    }

    public void setFacFullName(String facFullName) {
        this.facFullName = facFullName;
    }

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public List<String> getYear() {
		return year;
	}

	public void setYear(List<String> year) {
		this.year = year;
	}
	
	

}
