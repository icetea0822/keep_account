package com.shengzidong.keepacounts.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shengzidong.keepacounts.R;
import com.shengzidong.keepacounts.adapter.CategoryListAdapter;
import com.shengzidong.keepacounts.adapter.ReceiptAdapter;
import com.shengzidong.keepacounts.entity.Category;
import com.shengzidong.keepacounts.entity.Receipt;
import com.shengzidong.keepacounts.utils.Calculator;
import com.shengzidong.keepacounts.utils.MyCategory;
import com.shengzidong.keepacounts.utils.MyDataBaseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


public class AccountFragment extends BaseFragment implements View.OnClickListener {

    Button btnOk;
    ImageButton ibNumber_0, ibNumber_1, ibNumber_2, ibNumber_3,
            ibNumber_4, ibNumber_5, ibNumber_6, ibNumber_7,
            ibNumber_8, ibNumber_9, ibNumber_plus, ibNumber_minus,
            ibNumber_equals, ibNumber_point, ibNumber_delete, ibDate,
            ibLabel, ibMessage, ibLocation;
    TextView tvChoose;
    TextView tvMoney;

    private String address;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Bundle bundle = message.getData();
            String json = bundle.getString("json");
            address = getAddressFromJson(json);

            return false;
        }
    });


    double result;
    String money_input;

    CategoryListAdapter categoryListAdapter;
    ListView lvCategory;

    RecyclerView recyclerView;
    ReceiptAdapter recyclerViewAdapter;
    ArrayList<Receipt> receipts;


    ArrayList<Category> categories;
    private MyDataBaseUtils db;
    Location location;
    private String date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        initViews(view);
        setListeners();
        money_input = "";
        ibNumber_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money_input.length() > 0) {
                    money_input = "";
                }
                tvMoney.setText(money_input);
                if (money_input.length() <= 0) {
                    tvMoney.setText("输入金额");
                }

            }
        });
        db = new MyDataBaseUtils(getContext());

        setCategoryAdapter();
        setReceiptAdapter();


        return view;
    }


    public String getAddressByLocation(Location location) {
        BufferedReader in = null;
        String result = "";
        try {
            String url = "http://api.map.baidu.com/geocoder?location=" + location.getLatitude() + "," + location.getLongitude() + "&output=json";
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }


    private String getAddressFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            if (status.equals("OK")) {
                JSONObject result = jsonObject.getJSONObject("result");
                String address = result.getString("formatted_address");
                return address;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void setReceiptAdapter() {
        receipts = db.queryFromDataBase(86400000);
        if (receipts == null) {
            receipts = new ArrayList<>();
        }
        Log.d("AccountFragment", "查询保存的账单:" + receipts.toString());
        Receipt receipt = new Receipt();
        receipt.setCategory("");
        receipts.add(0, receipt);
        recyclerViewAdapter = new ReceiptAdapter(receipts, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    private void setCategoryAdapter() {

        MyCategory myCategory = new MyCategory(getContext());
        myCategory.setCategoryList(null, null);
        categories = myCategory.getCategoryList();
        //为类别列表排列顺序
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category category, Category t1) {
                if (category.getId() < t1.getId()) {
                    return -1;
                } else if (category.getId() > t1.getId()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        categoryListAdapter = new CategoryListAdapter(getActivity(), categories);
        lvCategory.setAdapter(categoryListAdapter);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvChoose.setText(categories.get(i).getName());
            }
        });
    }


    public Receipt saveAccount(String categorys, float money, String date, String lable, String message, String location) {
        if (categorys.equals("选择类别")) {
            Toast.makeText(getContext(), "请选择类别", Toast.LENGTH_SHORT).show();
            return null;
        }

        Receipt receipt = new Receipt();
        receipt.setCategory(categorys);
        receipt.setDate(date);
        receipt.setLabel(lable);
        receipt.setLocation(location);
        receipt.setMessage(message);
        receipt.setMoney(money);
        receipt.setTime(System.currentTimeMillis());
        Log.d("123", receipt.toString());
        return receipt;
    }

    @Override
    public void onClick(View view) {
        if (money_input.length() > 10) {
            Toast.makeText(getContext(), "您这么有钱，雇个会计吧！", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.btnOk:
                try {
                    String category = tvChoose.getText().toString().trim();

                    String tv = tvMoney.getText().toString();
                    float money = Float.parseFloat(tv);

                    if (date == null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        date = dateFormat.format(new java.util.Date());
                    }

                    String lable = null;
                    String message = null;
                    Receipt receipt = saveAccount(category, money, date, lable, message, address);

                    if (receipt != null && receipt.getCategory() != null) {
                        receipts.add(receipt);
                        recyclerViewAdapter.notifyItemChanged(receipts.size());
                        recyclerViewAdapter.notifyItemChanged(0);
                        recyclerView.smoothScrollToPosition(receipts.size());
                        db.insertToDataBase(receipt);

                    }
                    tvChoose.setText("选择类别");
                    money_input = "";
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ibNumber_0:
                money_input = money_input + "0";
                break;
            case R.id.ibNumber_1:
                money_input = money_input + "1";
                break;
            case R.id.ibNumber_2:
                money_input = money_input + "2";
                break;
            case R.id.ibNumber_3:
                money_input = money_input + "3";
                break;
            case R.id.ibNumber_4:
                money_input = money_input + "4";
                break;
            case R.id.ibNumber_5:
                money_input = money_input + "5";
                break;
            case R.id.ibNumber_6:
                money_input = money_input + "6";
                break;
            case R.id.ibNumber_7:
                money_input = money_input + "7";
                break;
            case R.id.ibNumber_8:
                money_input = money_input + "8";
                break;
            case R.id.ibNumber_9:
                money_input = money_input + "9";
                break;
            case R.id.ibNumber_plus:
                if (money_input.length() > 0) {
                    money_input = money_input + "+";
                }
                break;

            case R.id.ibNumber_minus:
                if (money_input.length() > 0) {
                    money_input = money_input + "-";
                }
                break;

            case R.id.ibNumber_equals:
                try {
                    Calculator calculator = new Calculator();
                    result = calculator.calculate(money_input);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "你输得什么玩意儿", Toast.LENGTH_SHORT).show();
                    result = 0;
                    return;
                }

                if (result == (int) result) {
                    tvMoney.setText((int) result + "");
                } else {
                    tvMoney.setText(result + "");
                }
                money_input = "";
                break;
            case R.id.ibNumber_point:
                money_input = money_input + ".";
                break;
            case R.id.tvChoose:
                break;
            case R.id.ibDate:
                final Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Log.d("123", "您选择的日期" + year + "-" + (month + 1) + "-" + day);
                        String mMonth = String.valueOf(month + 1);
                        String mday = String.valueOf(day);

                        if (mMonth.length() == 1) {
                            mMonth = "0" + mMonth;
                        }
                        if (mday.length() == 1) {
                            mday = "0" + mday;
                        }

                        date = year + "-" + mMonth + "-" + mday;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;
            case R.id.ibLabel:
                break;
            case R.id.ibMessage:
                break;
            case R.id.ibLocation:
                if (!ibLocation.isSelected()) {
                    ibLocation.setSelected(true);
                    Toast.makeText(getContext(), "定位已开启", Toast.LENGTH_SHORT).show();
                    //获得位置服务的名称
                    String serviceName = getContext().LOCATION_SERVICE;
                    //获得位置服务的管理对象
                    LocationManager locationManager = (LocationManager) getContext().getSystemService(serviceName);
                    // 通过GPS获取定位的位置数据
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Criteria criteria = new Criteria();
                        criteria.setAltitudeRequired(false);
                        criteria.setBearingRequired(false);
                        criteria.setCostAllowed(true);
                        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
                        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
                        location = locationManager.getLastKnownLocation(provider);

                        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo == null || !networkInfo.isAvailable()) {
                            //当前无可用网络
                            Toast.makeText(getContext(), "暂无网络", Toast.LENGTH_SHORT).show();
                        } else {
                            //当前有可用网络
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String result = getAddressByLocation(location);
                                    Message msg = new Message();
                                    Bundle data = new Bundle();
                                    data.putString("json", result);
                                    msg.setData(data);
                                    handler.sendMessage(msg);
                                }
                            }).start();
                        }
                    }
                } else if (ibLocation.isSelected()) {
                    ibLocation.setSelected(false);
                    Toast.makeText(getContext(), "定位已禁用", Toast.LENGTH_SHORT).show();
                    address = null;
                }
                break;
        }
        if (money_input.length() == 0 && result == 0) {
            tvMoney.setText("输入金额");
        } else if (money_input.length() > 0 && result == 0) {
            tvMoney.setText(money_input);
        }
        result = 0;
    }


    private void setListeners() {
        btnOk.setOnClickListener(this);
        ibNumber_0.setOnClickListener(this);
        ibNumber_1.setOnClickListener(this);
        ibNumber_2.setOnClickListener(this);
        ibNumber_3.setOnClickListener(this);
        ibNumber_4.setOnClickListener(this);
        ibNumber_5.setOnClickListener(this);
        ibNumber_6.setOnClickListener(this);
        ibNumber_7.setOnClickListener(this);
        ibNumber_8.setOnClickListener(this);
        ibNumber_9.setOnClickListener(this);
        ibNumber_plus.setOnClickListener(this);
        ibNumber_minus.setOnClickListener(this);
        ibNumber_equals.setOnClickListener(this);
        ibNumber_point.setOnClickListener(this);
        ibNumber_delete.setOnClickListener(this);
        tvChoose.setOnClickListener(this);
        ibDate.setOnClickListener(this);
        ibLabel.setOnClickListener(this);
        ibMessage.setOnClickListener(this);
        ibLocation.setOnClickListener(this);
    }

    private void initViews(View view) {

        btnOk = (Button) view.findViewById(R.id.btnOk);
        ibNumber_0 = (ImageButton) view.findViewById(R.id.ibNumber_0);
        ibNumber_1 = (ImageButton) view.findViewById(R.id.ibNumber_1);
        ibNumber_2 = (ImageButton) view.findViewById(R.id.ibNumber_2);
        ibNumber_3 = (ImageButton) view.findViewById(R.id.ibNumber_3);
        ibNumber_4 = (ImageButton) view.findViewById(R.id.ibNumber_4);
        ibNumber_5 = (ImageButton) view.findViewById(R.id.ibNumber_5);
        ibNumber_6 = (ImageButton) view.findViewById(R.id.ibNumber_6);
        ibNumber_7 = (ImageButton) view.findViewById(R.id.ibNumber_7);
        ibNumber_8 = (ImageButton) view.findViewById(R.id.ibNumber_8);
        ibNumber_9 = (ImageButton) view.findViewById(R.id.ibNumber_9);
        ibNumber_plus = (ImageButton) view.findViewById(R.id.ibNumber_plus);
        ibNumber_minus = (ImageButton) view.findViewById(R.id.ibNumber_minus);
        ibNumber_equals = (ImageButton) view.findViewById(R.id.ibNumber_equals);
        ibNumber_point = (ImageButton) view.findViewById(R.id.ibNumber_point);
        ibNumber_delete = (ImageButton) view.findViewById(R.id.ibNumber_delete);
        tvChoose = (TextView) view.findViewById(R.id.tvChoose);
        tvMoney = (TextView) view.findViewById(R.id.tvMoney);
        ibDate = (ImageButton) view.findViewById(R.id.ibDate);
        ibLabel = (ImageButton) view.findViewById(R.id.ibLabel);
        ibMessage = (ImageButton) view.findViewById(R.id.ibMessage);
        ibLocation = (ImageButton) view.findViewById(R.id.ibLocation);

        lvCategory = (ListView) view.findViewById(R.id.lvCategory);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.closeDataBase();
    }
}
