
package th.co.baiwa.excise.ia.persistence.vo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMobileCheckIncomeExciseAudit {

    @SerializedName("createBy")
    @Expose
    private String createBy;
    
    @SerializedName("assignTo")
    @Expose
    private String assignTo;
    
    @SerializedName("datas")
    @Expose
    private List<Data> datas = null;
    

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

}
