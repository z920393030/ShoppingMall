package com.atguigu.shoppingmall.ShoppingCart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    public static CartStorage instance;
    private static Context mContext;
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage() {
        sparseArray = new SparseArray<>();
        listTosparseArray();
    }

    private void listTosparseArray() {
        ArrayList<GoodsBean> datas = getAllData();
        for (int i = 0; i < datas.size(); i++) {
            GoodsBean goodsBean = datas.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);

        }
    }

    public ArrayList<GoodsBean> getAllData() {
        return getLocalData();
    }

    private ArrayList<GoodsBean> getLocalData() {
        ArrayList<GoodsBean> datas = new ArrayList<>();
        String saveJson = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(saveJson)) {
            datas = new Gson().fromJson(saveJson, new TypeToken<ArrayList<GoodsBean>>() {
            }.getType());
        }
        return datas;
    }


    public static CartStorage getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            synchronized (CartStorage.class) {
                if (instance == null) {
                    instance = new CartStorage();
                }
            }
        }

        return instance;
    }

    public void addData(GoodsBean bean) {
        GoodsBean temp = sparseArray.get(Integer.parseInt(bean.getProduct_id()));
        if (temp != null) {
            temp.setNumber(bean.getNumber());
        } else {
            temp = bean;
        }

        sparseArray.put(Integer.parseInt(temp.getProduct_id()), temp);


        commit();


    }


    public void deleteData(GoodsBean bean) {
        sparseArray.delete(Integer.parseInt(bean.getProduct_id()));
        commit();

    }


    public void updateData(GoodsBean bean) {
        sparseArray.put(Integer.parseInt(bean.getProduct_id()),bean);
        commit();

    }

    private void commit() {
        ArrayList<GoodsBean> goodsBeens = sparseArrayToList();
        String json = new Gson().toJson(goodsBeens);
        CacheUtils.putString(mContext,JSON_CART,json);


    }

    private ArrayList<GoodsBean> sparseArrayToList() {
        ArrayList<GoodsBean> goodsBeens = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeens.add(goodsBean);
        }
        return goodsBeens;
    }


    public GoodsBean findData(String product_id) {
        GoodsBean goodsBean = sparseArray.get(Integer.parseInt(product_id));
        return goodsBean;
    }
}
