package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;


public class Order {
    private Collection<Product> products;
    private double finalPrice;
    private double satisfactionFee;
    private double shippingCosts;
    private OrderSize size;
    private OrderStatus status;
    private LocalDate creationDate;


    private static final double SERVICE_FEE_NEW = 0.5;
    private static final double SERVICE_FEE_USED = 0.25;

    public Order(Collection<Product> products,double finalPrice,double satisfactionFee,double shippingCosts,OrderSize size,OrderStatus status,LocalDate creationDate) {
        this.products = products;
        this.finalPrice = finalPrice;
        this.satisfactionFee = satisfactionFee;
        this.shippingCosts = shippingCosts;
        this.size = size;
        this.status = status;
        this.creationDate = creationDate;
    }

    public Order() {
        this.products = new HashSet<>();
        this.finalPrice = 0.0;
        this.satisfactionFee = 0.0;
        this.shippingCosts = 0.0;
        this.size = OrderSize.MEDIUM;
        this.status = OrderStatus.PENDING;
        this.creationDate = LocalDate.now();
    }

    public Order(Order order) {
        this.products = order.getProducts();
        this.finalPrice = order.getFinalPrice();
        this.satisfactionFee = order.getSatisfactionFee();
        this.shippingCosts = order.getShippingCosts();
        this.size = order.getSize();
        this.status = order.getStatus();
        this.creationDate = order.getCreationDate();
    }



    public void addProduct(Product product) {
        products.add(product);
        updateFinalPrice();
    }

    public void removeProduct(Product product) throws EmptyOrderException{
        products.remove(product);
        updateFinalPrice();
        if(this.products.isEmpty()){
            throw new EmptyOrderException("Order is empty after removing item, removing order");
        }
    }



    public double calculateFinalPrice() {
        double finalPrice = 0.0;

        for (Collection<Product> products: productss) {
            if (productss.status = NEW) {
                this.satisfactionFee += SERVICE_FEE_NEW;
                finalPrice += productss.getBasePrice() + SERVICE_FEE_NEW;
            } else if (productss.status = USED) {
                this.satisfactionFee += SERVICE_FEE_USED;
                finalPrice += productss.getBasePrice() +  SERVICE_FEE_USED;
            }
        }

        finalPrice += shippingCosts;

        return finalPrice;
    }

    public boolean returnOrder(LocalDate currentDate, LocalDate deadline) {
        if (currentDate.isBefore(deadline) && status == OrderStatus.FINALIZED) {
            status = OrderStatus.PENDING;
            return true;
        }
        return false;
    }

    public Order returnOrder() throws OrderNotEligibleForReturnException {
        LocalDateTime now = LocalDateTime.now();
        Duration timeDifference = Duration.between(this.creationDate, now);

        if ((this.status == OrderStatus.FINALIZED) && timeDifference.toHours() <= 48) {
            return this; ///???
        } else {
            throw new OrderNotEligibleForReturnException("Order is not eligible for return as more than 48 hours have passed.");
        }
    }



    public Collection<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public double getFinalPrice() {
        return this.finalPrice;
    }

    public void setFinalPrice(double finalPrice){
        this.finalPrice = finalPrice;
    }

    public double getSatisfactionFee() {
        return this.satisfactionFee;
    }

    public void setSatisfactionFee(double satisfactionFee) {
        this.satisfactionFee = satisfactionFee;
    }


    public double getShippingCosts() {
        return this.shippingCosts;
    }

    public void setShippingCosts(double shippingCosts) {
        this.shippingCosts = shippingCosts;
    }


    public OrderSize getSize() {
        return this.size;
    }

    public void setSize(OrderSize size) {
        this.size = size;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Order clone(){
        return new Order(this);
    }

    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if(obj==null || obj.getClass() != this.getClass())
            return false;
        Order order = (Order) obj;
        return order.getProducts().equals(this.products) &&
                order.getFinalPrice() == this.finalPrice &&
                order.getSatisfactionFee() == this.satisfactionFee &&
                order.getShippingCosts() == this.shippingCosts &&
                order.getSize() == this.size &&
                order.getStatus() == this.status &&
                order.getCreationDate() == this.creationDate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order:: {");
        sb.append("Products: ").append(this.products);
        sb.append("Final Price: ").append(this.finalPrice);
        sb.append("Satisfaction Fee: ").append(this.satisfactionFee);
        sb.append("Shipping Costs: ").append(this.shippingCosts);
        sb.append("Size: ").append(this.size);
        sb.append("Status: ").append(this.status);
        sb.append("Creation Date: ").append(this.creationDate).append("}");
        return sb.toString();
    }

}

