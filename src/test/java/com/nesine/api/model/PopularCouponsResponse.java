package com.nesine.api.model;

import java.util.List;

/**
 * sc: body icindeki status kodu, d: kuponlar, el: hata listesi.
 */
public class PopularCouponsResponse {

    private int sc;
    private List<Coupon> d;
    private List<ApiError> el;

    public int getSc() {
        return sc;
    }

    public List<Coupon> getD() {
        return d;
    }

    public List<ApiError> getEl() {
        return el;
    }
}
