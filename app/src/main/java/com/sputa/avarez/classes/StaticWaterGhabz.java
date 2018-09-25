package com.sputa.avarez.classes;

public class StaticWaterGhabz {

    public static String ghabz1_eshteraak = "123";
    public static String ghabz1_shenase = "321";


    public static String ghabz1_name = "رحمان احمدی";
    public static String ghabz2_name = "یاسر ولی زاده";


    public static String ghabz1_price_number = "295,000";
    public static String ghabz2_price_number = "320,000";

    public static String ghabz1_price_number_simple = "295000";
    public static String ghabz2_price_number_simple = "320000";

    public static String ghabz1_ID = "3";
    public static String ghabz2_ID = "4";

    public static String ghabz1_price = "  :  رحمان احمدی"+ "\n"+ "  :  295,000" + "\n" + "  :  170,000" + "\n" + "  :  3400 لیتر";
    public static String ghabz1_detail =" :  97/03/24"+"\n"+" :  97/04/26"+"\n"+" :  389"+"\n"+" :  423"+"\n"+" :  166,655"+"\n"+" :  110,588"+"\n"+" :  21,889"+"\n"+" :  5,100"+"\n"+" :  295,000";


    public static String ghabz2_eshteraak = "456";
    public static String ghabz2_shenase = "654";
    public static String ghabz2_price = "  :  یاسر ولی زاده"+ "\n"+ "  :  320,000" + "\n" + "  :  170,000" + "\n" + "  :  4200 لیتر";
    public static String ghabz2_detail =" :  97/03/24"+"\n"+" :  97/04/26"+"\n"+" :  845"+"\n"+" :  960"+"\n"+" :  254,655"+"\n"+" :  260,588"+"\n"+" :  21,889"+"\n"+" :  5,100"+"\n"+" :  320,000";


    public static String get_ghabz_ID_eshterak(String eshterak)
    {
        String ret="11";
        if(eshterak.equals(ghabz1_eshteraak))
        {
            ret = ghabz1_ID;
        }
        if(eshterak.equals(ghabz2_eshteraak))
        {
            ret = ghabz2_ID;
        }
        return ret;
    }
       public static String get_ghabz_ID_shenase(String shenase)
    {
        String ret="11";
        if(shenase.equals(ghabz1_shenase))
        {
            ret = ghabz1_ID;
        }
        if(shenase.equals(ghabz2_shenase))
        {
            ret = ghabz2_ID;
        }
        return ret;
    }


    public static String get_ghabz_price_eshterak(String eshterak)
    {
        String ret="11";
        if(eshterak.equals(ghabz1_eshteraak))
        {
            ret = ghabz1_price;
        }
        if(eshterak.equals(ghabz2_eshteraak))
        {
            ret = ghabz2_price;
        }
        return ret;
    }
    public static String get_ghabz_price_shenase(String shenase)
    {
        String ret="11";
        if(shenase.equals(ghabz1_shenase))
        {
            ret = ghabz1_price;
        }
        if(shenase.equals(ghabz2_shenase))
        {
            ret = ghabz2_price;
        }
        return ret;
    }
    public static String get_ghabz_detail_eshterak(String eshterak)
    {
        String ret="11";
        if(eshterak.equals(ghabz1_eshteraak))
        {
            ret = ghabz1_detail;
        }
        if(eshterak.equals(ghabz2_eshteraak))
        {
            ret = ghabz2_detail;
        }
        return ret;
    }
    public static String get_ghabz_detail_shenase(String shenase)
    {
        String ret="11";
        if(shenase.equals(ghabz1_shenase))
        {
            ret = ghabz1_detail;
        }
        if(shenase.equals(ghabz2_shenase))
        {
            ret = ghabz2_detail;
        }
        return ret;
    }


}
