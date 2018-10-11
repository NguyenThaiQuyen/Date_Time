package dhbkdn.it.dut.appschedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkInWeek {
    private String nameWork;
    private String contentWork;
    private Date dealineDate;
    private Date dealineHour;

    public WorkInWeek(String nameWork, String contentWork, Date dealineDate, Date dealineHour) {
        super();
        this.nameWork = nameWork;
        this.contentWork = contentWork;
        this.dealineDate = dealineDate;
        this.dealineHour = dealineHour;
    }

    public WorkInWeek() {
        super();
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public String getContentWork() {
        return contentWork;
    }

    public void setContentWork(String contentWork) {
        this.contentWork = contentWork;
    }

    public Date getDealineDate() {
        return dealineDate;
    }

    public void setDealineDate(Date dealineDate) {
        this.dealineDate = dealineDate;
    }

    public Date getDealineHour() {
        return dealineHour;
    }

    public void setDealineHour(Date dealineHour) {
        this.dealineHour = dealineHour;
    }

    // lấy định đạng ngày
    public String getDateFormat(Date date){
        SimpleDateFormat d;
        d= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return d.format(date);
    }

    // lấy định đạng giờ
    public String getHourFormat(Date date){
        SimpleDateFormat d;
        d= new SimpleDateFormat("hh: mm a", Locale.getDefault());
        return d.format(date);
    }

    @Override
    public String toString() {
        return this.nameWork + " " + getDateFormat(this.dealineDate) + " - " + getHourFormat(this.dealineHour);
    }
}
