import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class SelectionWindow extends JFrame {
    Item item;
    JComboBox unitSwitcher;
    JComboBox quantityBox;
    JButton okButton;
    JButton cancelButton;
    JPanel okPanel;
    JPanel cancelPanel;

    ActionListener actionListener;
    ItemListener itemListener;

    final Double[] servingUnits = new Double[]{.25,.5,.75,1.0,1.25,1.5,1.75,2.0,2.25,2.5,2.75,3.0,3.25,3.5,3.75,4.0,4.25,4.5,4.75,5.0};
    String[] units;
    Double[] otherUnits = new Double[16];

    SelectionWindow(Item item, double otherUnit, ActionListener actionListener, ItemListener itemListener){
        super();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Adding "+item.name);
        setVisible(true);
        setBounds(100,200,300,130);

        this.actionListener = actionListener;
        this.itemListener = itemListener;

        this.item = item;
        double base = otherUnit/4;

        units = new String[]{"servings",item.serving.substring(item.serving.indexOf((int)otherUnit)+String.valueOf(otherUnit).length()+1)};


        for(int i = 0; i < 16; i++){
            otherUnits[i] = base*(i+1);
        }
        okPanel = new JPanel();
        cancelPanel = new JPanel();
        quantityBox = new JComboBox(servingUnits);
        unitSwitcher = new JComboBox(units);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        quantityBox.setBounds(40,30,100,30);
        unitSwitcher.setBounds(160,30,100,30);
        okPanel.setBounds(160,150,70,40);
        cancelButton.setBounds(40,100,70,40);

        okButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
        unitSwitcher.addItemListener(itemListener);

        add(quantityBox);
        add(unitSwitcher);
        add(okPanel);
        add(cancelPanel);
        okPanel.add(okButton);
        cancelPanel.add(cancelButton);


    }

}
