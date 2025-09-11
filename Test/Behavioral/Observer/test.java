// Problematic Subject - Directly manages its "observers"

import java.util.ArrayList;
import java.util.List;

interface StockDataObserver {
    void update(double price);
}

interface StockDataSubjects {
    void register(StockDataObserver stockDataObserver);
    void deregister(StockDataObserver stockDataObserver);
    void notifySub(double price);
}


class Stock implements StockDataSubjects{
    private String symbol;
    private double price;
    List<StockDataObserver> subscribers = new ArrayList<>();
    
    // // Direct references to display objects - Problematic!
    // private CurrentPriceDisplay currentPriceDisplay;
    // private TradingAlerts tradingAlerts;

    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        System.out.println("Stock: " + symbol + " initialized at $" + price);
    }

    public void notifySub(double price){
        setPrice(price);
    } 

    // Problem: This method is tightly coupled to specific display types.
    public void setPrice(double newPrice) {
        System.out.println("\nStock: " + symbol + " price changed from $" + this.price + " to $" + newPrice);
        this.price = newPrice;

        // Directly updating displays - Problematic!
        // if (currentPriceDisplay != null) {
        //     currentPriceDisplay.updatePrice(newPrice);
        // }
        // if (tradingAlerts != null) {
        //     tradingAlerts.checkPrice(newPrice);
        // }
        for(StockDataObserver subscriber : subscribers){
            subscriber.update(newPrice);
        }
        // If we add a new display type (e.g., HistoricalChart), we'd have to modify this method.
    }

    // Methods to "register" displays (still problematic as they are specific types)
    @Override
    public void register(StockDataObserver stockDataObserver) {
        // this.currentPriceDisplay = display;
        subscribers.add(stockDataObserver);
    }

    @Override
    public void deregister(StockDataObserver stockDataObserver) {
        //this.tradingAlerts = alerts;
        subscribers.remove(stockDataObserver);
    }

}

// Problematic Observer - Directly updated by the Subject
class CurrentPriceDisplay implements StockDataObserver{
    @Override
    public void update(double newPrice) {
        System.out.println("CurrentPriceDisplay: New price is $" + newPrice);
    }
}

// Problematic Observer - Directly updated by the Subject
class TradingAlerts implements StockDataObserver {
    private double alertThreshold;

    public TradingAlerts(double threshold) {
        this.alertThreshold = threshold;
    }

    @Override
    public void update(double currentPrice) {
        if (currentPrice > alertThreshold) {
            System.out.println("TradingAlerts: ALERT! Price (" + currentPrice + ") is above threshold (" + alertThreshold + ")");
        } else {
            System.out.println("TradingAlerts: Price (" + currentPrice + ") is below threshold (" + alertThreshold + ")");
        }
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        StockDataSubjects googleStock = new Stock("GOOG", 1500.00);

        StockDataObserver priceDisplay = new CurrentPriceDisplay();
        StockDataObserver alerts = new TradingAlerts(1550.00);

        // Problem: Stock directly registers and updates specific display types.
        googleStock.register(priceDisplay);
        googleStock.register(alerts);

        System.out.println("--- Initial Price ---");
        googleStock.notifySub(1500.00); // Set initial price to trigger updates

        System.out.println("\n--- Price Update 1 ---");
        googleStock.notifySub(1520.00);

        System.out.println("\n--- Price Update 2 (Above Threshold) ---");
        googleStock.notifySub(1560.00);

        System.out.println("\nProblem: The Stock class is tightly coupled to specific display types.");
        System.out.println("Adding a new display type requires modifying the Stock class.");
        System.out.println("This violates the Open/Closed Principle.");
    }
}
