import java.math.BigDecimal;
import java.util.Objects;

public class Smartphone {
    private String name;
    private String brande;
    private BigDecimal screenDiagonal;
    private MartixType martixType;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrande() {
        return brande;
    }

    public void setBrande(String brande) {
        this.brande = brande;
    }

    public BigDecimal getScreenDiagonal() {
        return screenDiagonal;
    }

    public void setScreenDiagonal(BigDecimal screenDiagonal) {
        this.screenDiagonal = screenDiagonal;
    }

    public MartixType getMartixType() {
        return martixType;
    }

    public void setMartixType(MartixType martixType) {
        this.martixType = martixType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Smartphone that = (Smartphone) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(brande, that.brande) &&
                Objects.equals(screenDiagonal, that.screenDiagonal) &&
                martixType == that.martixType &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum MartixType {
        IPS, SuperAmoled, OLED, Other
    }
}
