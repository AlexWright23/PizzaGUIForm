import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//GUI, size, toppings, crust, receipt
public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDish, stuffedCrust;
    private JComboBox<String> sizeCombo;
    private JCheckBox[] toppings;
    private JTextArea orderSummary;
    private JButton orderButton, clearButton, quitButton;

    //pricing
    private static final double TAX_RATE = 0.07;
    private static final double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};
    private static final String[] SIZES = {"Small", "Medium", "Large", "Super"};
    private static final String[] TOPPING_NAMES = {"Pepperoni", "Mushrooms", "Olives", "Bacon", "Pineapple", "Extra Cheese"};

    //constructor

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        //crust selection
        JPanel crustPanel = new JPanel(new GridLayout(4, 1));
        crustPanel.setBorder(new TitledBorder("Choose Crust Type"));
        thinCrust = new JRadioButton("Thin Crust");
        regularCrust = new JRadioButton("Regular Crust");
        deepDish = new JRadioButton("Deep Dish");
        stuffedCrust = new JRadioButton("Stuffed Crust");
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDish);
        crustGroup.add(stuffedCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDish);
        crustPanel.add(stuffedCrust);

        //size selection
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder("Choose Size"));
        sizeCombo = new JComboBox<>(SIZES);
        sizePanel.add(sizeCombo);

        //toppings selection
//        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
//        toppingsPanel.setBorder(new TitledBorder("Choose Toppings ($1 each)"));
//        toppings = new JCheckBox[TOPPING_NAMES.length];
//        for (int i = 0; i < TOPPING_NAMES.length; i++) {
//            toppings[i] = new JCheckBox(TOPPING_NAMES[i]);
//            toppingsPanel.add(toppings[i]);
//        }
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(0, 2, 5, 5));
        toppingsPanel.setBorder(new TitledBorder("Choose Toppings ($1 each)"));
        toppingsPanel.setPreferredSize(new Dimension(250, 100));

        toppings = new JCheckBox[TOPPING_NAMES.length];

        for (int i = 0; i < TOPPING_NAMES.length; i++) {
            toppings[i] = new JCheckBox(TOPPING_NAMES[i]);
            toppingsPanel.add(toppings[i]);
        }

        // order summary
        JPanel orderPanel = new JPanel();
        orderSummary = new JTextArea(10, 30);
        orderSummary.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        orderPanel.setBorder(new TitledBorder("Order Summary"));
        orderPanel.add(scrollPane);

        //buttons
        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        add(crustPanel, BorderLayout.WEST);
        add(sizePanel, BorderLayout.NORTH);
        add(toppingsPanel, BorderLayout.CENTER);
        add(orderPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        //action listeners
        orderButton.addActionListener(new OrderListener());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> confirmExit());
    }

    //OrderListener
    private class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!thinCrust.isSelected() && !regularCrust.isSelected() && !deepDish.isSelected() && !stuffedCrust.isSelected()) {
                JOptionPane.showMessageDialog(null, "Please select a crust type.");
                return;
            }
            int sizeIndex = sizeCombo.getSelectedIndex();
            double basePrice = SIZE_PRICES[sizeIndex];

            int toppingCount = 0;
            StringBuilder toppingList = new StringBuilder();
            for (JCheckBox topping : toppings) {
                if (topping.isSelected()) {
                    toppingCount++;
                    toppingList.append(topping.getText()).append(" ($1)\n");
                }
            }
            if (toppingCount == 0) {
                JOptionPane.showMessageDialog(null, "Please select at least one topping.");
                return;
            }

            double subtotal = basePrice + toppingCount;
            double tax = subtotal * TAX_RATE;
            double total = subtotal + tax;

            String crust = thinCrust.isSelected() ? "Thin Crust" :
                    regularCrust.isSelected() ? "Regular Crust" :
                            deepDish.isSelected() ? "Deep Dish" : "Stuffed Crust";

            orderSummary.setText(
                    "==========================================\n" +
                            "Type of Crust & Size\t\tPrice\n" +
                            crust + " + " + SIZES[sizeIndex] + "\t\t$" + basePrice + "\n" +
                            "------------------------------------------\n" +
                            "Ingredients\t\tPrice\n" +
                            toppingList.toString() +
                            "------------------------------------------\n" +
                            "Sub-total:\t\t$" + String.format("%.2f", subtotal) + "\n" +
                            "Tax:\t\t$" + String.format("%.2f", tax) + "\n" +
                            "------------------------------------------\n" +
                            "Total:\t\t$" + String.format("%.2f", total) + "\n" +
                            "=========================================="
            );
        }
    }

    //clears selections
    private void clearForm() {
        sizeCombo.setSelectedIndex(0);
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDish.setSelected(false);
        stuffedCrust.setSelected(false);
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        orderSummary.setText("");
    }

    //order confirmation
    private void confirmExit() {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
