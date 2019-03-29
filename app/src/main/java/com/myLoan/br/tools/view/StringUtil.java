package com.myLoan.br.tools.view;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.myLoan.br.MyApplication;
import com.myLoan.br.R;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class StringUtil {

    public static String checkUrl(String url) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            return "http://" + url;
        }
        return url;
    }

    public static String dealPhone(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            if (phoneNum.length() >= 8) {
                return phoneNum.substring(0, 3) + "***" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
            }
        }
        return "";
    }


    public static String getLanguage() {
        Locale locale = MyApplication.getMyApplication().getResources().getConfiguration().locale;
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static String[] getCountryZipCode() {//   HashMap<Integer, String> countryCodeMap = new HashMap<>();
        String CountryID = "";
        String[] CountryZipCode = new String[2];
        TelephonyManager manager = (TelephonyManager) MyApplication.getMyApplication().getSystemService(MyApplication.getMyApplication().TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        if (TextUtils.isEmpty(CountryID)) {
            Locale locale = MyApplication.getMyApplication().getResources().getConfiguration().locale;
            CountryID = locale.getCountry();
        }
        String[] rl = MyApplication.getMyApplication().getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode[0] = g[0];
                CountryZipCode[1] = g[2];

                break;
            }
        }
        return CountryZipCode;
    }

  /*  public static List<CountryEntity> getCountry() {//   HashMap<Integer, String> countryCodeMap = new HashMap<>();
        String CountryZipCode = "";
        List<CountryEntity> countryEntityList = new ArrayList<>();
        String[] rl = MyApplication.getMyApplication().getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            CountryEntity countryEntity = new CountryEntity();
            String[] g = rl[i].split(",");
            countryEntity.setCountryCode(g[0]);
            countryEntity.setCountryName(g[2]);
            countryEntityList.add(countryEntity);
        }
        return countryEntityList;
    }*/

    public static int getRamdomValue(int round, int min) {
        Random random = new Random();
        int value = random.nextInt(round) + min;
        return value;
    }


    /**
     * 处理信用卡，取后4位
     *
     * @return
     */
    public static String getCredictNo4(String credictNo) {
        if (credictNo == null) {
            return "";
        } else if (credictNo != null && credictNo.length() > 4) {
            return credictNo.substring(credictNo.length() - 4, credictNo.length());
        }
        return credictNo;
    }


    //Java中判断字符串是否全为数字的方法
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }
}
