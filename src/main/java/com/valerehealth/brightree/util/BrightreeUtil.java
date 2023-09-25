package com.valerehealth.brightree.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import com.valerehealth.brightree.model.brightree.BrightreeName;
import com.valerehealth.brightree.model.valere.ValereSubmitter;

public class BrightreeUtil {

    public static String localDateToYYYYMMDD(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.toString().replaceAll("-", "");
    }

    public static String localTimeToHHMM(LocalTime time) {
        if (time == null) {
            return null;
        }

        String timeString = time.toString();

        if (timeString.length() < 5) {
            return null;
        }

        return timeString.substring(0, 5);
    }

    public static String getSalesOrderStatus(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String status = CommonUtil.capitalFirstLetter(str.trim());

        if (status.equals("New") || status.equals("Delivered")) {
            return status;
        }

        return null;
    }

    public static BrightreeName getSubmittedBy(ValereSubmitter valereSubmitter) {
        if (valereSubmitter == null) {
            return null;
        }

        BrightreeName submitterName = new BrightreeName();

        submitterName.setFirst(valereSubmitter.getFirstName());
        submitterName.setMiddle(valereSubmitter.getMiddleName());
        submitterName.setLast(valereSubmitter.getLastName());

        return submitterName;
    }

    public static String getCountry(String str) {
        if (CommonUtil.hasValue(str)
                && (str.equals("USA") || str.equals("Can") || str.equals("Mex"))) {
            return str;
        }

        return null;
    }

    public static String getZipCode(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String zipCode = str.trim().replaceAll("-", "");

        if (zipCode.length() < 5) {
            return null;
        }

        if (zipCode.length() == 5) {
            return zipCode;
        }

        if (zipCode.length() == 9) {
            return zipCode;
        }

        return null;
    }

    public static String getGender(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String gender = str.trim().toLowerCase();

        if (gender.equals("m") || gender.equals("male")) {
            return "Male";
        }

        if (gender.equals("f") || gender.equals("female")) {
            return "Female";
        }

        return null;
    }

    public static String getMaritalStatus(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String maritalStatus = str.trim().toLowerCase();

        if (Arrays.asList("single", "married", "common-law", "divorced", "widowed", "unknown")
                .contains(maritalStatus)) {
            return CommonUtil.capitalFirstLetter(maritalStatus);
        }

        return null;
    }

    public static String getSsn(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String ssn = str.trim().replaceAll("-", "");

        if (ssn.length() == 9) {
            return ssn;
        }

        return null;
    }

    public static String getPhoneNumber(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String phoneNumber = str.replaceAll("[^0-9]", "");

        if (phoneNumber.length() == 10) {
            return phoneNumber;
        }

        if (phoneNumber.length() == 11 && phoneNumber.charAt(0) == '1') {
            return phoneNumber.substring(1);
        }

        return null;
    }

    public static String getEmail(String str) {
        if (!CommonUtil.hasValue(str)) {
            return null;
        }

        String[] emailParts = str.trim().split("@");

        if (emailParts.length != 2) {
            return null;
        }

        return str.trim();
    }

}
