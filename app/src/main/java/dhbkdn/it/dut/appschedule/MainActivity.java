package dhbkdn.it.dut.appschedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    TextView txtResultDate, txtResultTime;
    Button btnDate, btnTime, btnAddWork;
    EditText edtWork, edtContent;
    ArrayList<WorkInWeek> arrWork;
    ArrayAdapter<WorkInWeek> adapterWork;
    ListView lvListWork;
    Date mDate;
    Date mHour;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        getDefaultDateTime();
        addEvents();
    }

    private void addControls() {

        txtResultDate = findViewById(R.id.txtResultDate);
        txtResultTime = findViewById(R.id.txtResultTime);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnAddWork = findViewById(R.id.btnAddWork);
        edtWork = findViewById(R.id.edtWork);
        edtContent = findViewById(R.id.edtContent);
        lvListWork = findViewById(R.id.lvListWork);

        arrWork = new ArrayList<WorkInWeek>();

        adapterWork = new ArrayAdapter<WorkInWeek>(MainActivity.this, android.R.layout.simple_list_item_1, arrWork);
        lvListWork.setAdapter(adapterWork);

    }

    // hàm lấy thông số mặc định khi lần đầu tiên chạy hệ thống
    private void getDefaultDateTime() {
        String strDate = "", strTime = "";
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;

        // lấy ngày hệ thống
        // định dạng ngày tháng
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        strDate = dft.format(cal.getTime());
        // hiển thị lên textview
        txtResultDate.setText(strDate);

        // lấy giờ hệ thống
        // định dạng giờ phút
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        strTime = dft.format(cal.getTime());
        // hiển thị lên textview
        txtResultTime.setText(strTime);

        edtWork.requestFocus();
        // gán cal.getTime() cho ngày giờ dealine

        mDate = cal.getTime();
        mHour = cal.getTime();
    }

    private void addEvents() {
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAddWork.setOnClickListener(new MyButtonEvent());
        lvListWork.setOnItemClickListener(new MyListViewEvent());
        lvListWork.setOnItemLongClickListener(new MyListViewEvent());
    }

    private class MyButtonEvent implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDate:
                    showDatePickerDialog();
                    break;
                case R.id.btnTime:
                    showTimePickerDialog();
                    break;
                case R.id.btnAddWork:
                    processAddJob();
                    break;
            }
        }
    }

    private void processAddJob() {
        String nameWork = edtWork.getText() + "";
        String contentWork = edtContent.getText() + "";
        WorkInWeek job = new WorkInWeek(nameWork, contentWork, mDate, mHour);
        arrWork.add(job);
        adapterWork.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        edtWork.setText("");
        edtContent.setText("");
        edtWork.requestFocus();
    }


    public void showTimePickerDialog() {
        OnTimeSetListener callback=new OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s=hourOfDay +":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)  hourTam=hourTam-12;
                txtResultTime.setText(hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtResultTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                mHour=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtResultTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(MainActivity.this, callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }


    public void showDatePickerDialog() {
        OnDateSetListener callback = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                txtResultDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                mDate = cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = txtResultDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int day = Integer.parseInt(strArrtmp[0]);
        int month = Integer.parseInt(strArrtmp[1]) - 1;
        int year = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(MainActivity.this, callback, day, month, year);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    private class MyListViewEvent implements OnItemClickListener, OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            //Xóa vị trí thứ arg2
            arrWork.remove(arg2);
            adapterWork.notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //Hiển thị nội dung công việc tại vị trí thứ arg2
            Toast.makeText(MainActivity.this,
                    arrWork.get(arg2).getContentWork(),
                    Toast.LENGTH_LONG).show();
        }

    }
}
