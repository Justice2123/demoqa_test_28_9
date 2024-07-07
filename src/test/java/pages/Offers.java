package pages;


public class Offers {


    private String barcode;
    private String id;
    private Integer price_new;

    public Integer getPrice_new() {
        return price_new;
    }

    public void setPrice_new(Integer price_new) {
        this.price_new = price_new;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
