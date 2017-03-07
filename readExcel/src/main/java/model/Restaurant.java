package model;

/**
 * Created by onaple on 2017/3/6.
 */
public class Restaurant {
    public String shopName1;
    public String shopname2;
    public String shopName3;
    public Integer id1;
    public Integer id2;


    @Override
    public String toString() {
        return "Restaurant{" +
                "shopName1='" + shopName1 + '\'' +
                ", shopname2='" + shopname2 + '\'' +
                ", shopName3='" + shopName3 + '\'' +
                ", id1=" + id1 +
                ", id2=" + id2 +
                '}';
    }

    public Restaurant(){

    }

    public Restaurant(String shopName1, String shopname2, String shopName3, Integer id1, Integer id2) {
        this.shopName1 = shopName1;
        this.shopname2 = shopname2;
        this.shopName3 = shopName3;
        this.id1 = id1;
        this.id2 = id2;
    }

    public String getShopName1() {
        return shopName1;
    }

    public void setShopName1(String shopName1) {
        this.shopName1 = shopName1;
    }

    public String getShopname2() {
        return shopname2;
    }

    public void setShopname2(String shopname2) {
        this.shopname2 = shopname2;
    }

    public String getShopName3() {
        return shopName3;
    }

    public void setShopName3(String shopName3) {
        this.shopName3 = shopName3;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }
}
