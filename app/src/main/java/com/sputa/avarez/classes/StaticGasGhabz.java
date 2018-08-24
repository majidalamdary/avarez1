package com.sputa.avarez.classes;

public class StaticGasGhabz {

    public static String ghabz1_eshteraak = "123";
    public static String ghabz1_shenase = "321";


    public static String ghabz1_name = "احمد رضایی";
    public static String ghabz2_name = "جواد رضازاده";


    public static String ghabz1_price_number = "47,563";
    public static String ghabz2_price_number = "62,563";

    public static String ghabz1_price_number_simple = "47563";
    public static String ghabz2_price_number_simple = "62563";

    public static String ghabz1_ID = "1";
    public static String ghabz2_ID = "2";

    public     static String ghabz1_price = "  :  احمد رضایی"+ "\n"+ "  :  47,563" + "\n" + "  :  15,000" + "\n" + "  :  47 مترمکعب";
    public static String ghabz1_detail =" :  97/03/24"+"\n"+"\n"+" :  97/04/26"+"\n"+"\n"+" :  81424"+"\n"+"\n"+" :  81468"+"\n"+"\n"+" :  47"+"\n"+"\n"+" :  47,563"+"\n"+"\n"+" :  2";


    public static String ghabz2_eshteraak = "456";
    public static String ghabz2_shenase = "654";
    public static String ghabz2_price = "  :  جواد رضازاده"+ "\n"+ "  :  62,563" + "\n" + "  :  0" + "\n" + "  :  47 مترمکعب";
    public static String ghabz2_detail =" :  97/03/24"+"\n"+"\n"+" :  97/04/26"+"\n"+"\n"+" :  76324"+"\n"+"\n"+" :  76368"+"\n"+"\n"+" :  47"+"\n"+"\n"+" :  62,563"+"\n"+"\n"+" :  2";


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
