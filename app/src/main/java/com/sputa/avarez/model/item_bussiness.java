package com.sputa.avarez.model;

public class item_bussiness {

    private String _avarezYear;
    private String _penaltyRate;
    private String _avarezPrice;
    private String _avarezLate;
    private String _servicePrice;
    private String _serviceLate;
    private String _localServicePrice;
    private String _localServiceLate;

    public String get_avarezYear() {
        return _avarezYear;
    }

    public String get_penaltyRate() {
        return _penaltyRate;
    }

    public String get_avarezPrice() {
        return _avarezPrice;
    }

    public String get_avarezLate() {
        return _avarezLate;
    }

    public String get_servicePrice() {
        return _servicePrice;
    }

    public String get_serviceLate() {
        return _serviceLate;
    }

    public String get_localServicePrice() {
        return _localServicePrice;
    }

    public String get_localServiceLate() {
        return _localServiceLate;
    }

    public item_bussiness(String _avarezYear, String _penaltyRate, String _avarezPrice, String _avarezLate, String _servicePrice, String _serviceLate, String _localServicePrice, String _localServiceLate) {
        this._avarezYear = _avarezYear;
        this._penaltyRate = _penaltyRate;
        this._avarezPrice = _avarezPrice;
        this._avarezLate = _avarezLate;
        this._servicePrice = _servicePrice;
        this._serviceLate = _serviceLate;
        this._localServicePrice = _localServicePrice;
        this._localServiceLate = _localServiceLate;
    }
}
