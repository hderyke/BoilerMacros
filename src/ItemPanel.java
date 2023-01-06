import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemPanel extends JPanel {
    Item item;
    JLabel itemName;
    JLabel portionLabel;
    JLabel calorieLabel;
    JLabel macrosLabel;
    JButton moreInfo;
    int x;
    int y;
    ArrayList<String> coords;





    ItemPanel(Item item,int x, int y){
        coords = new ArrayList<>();
        this.item = item;
        itemName = new JLabel(item.name);
        calorieLabel = new JLabel("456 calories");
        portionLabel = new JLabel("1.5 cups (3 servings)");
        macrosLabel = new JLabel("C : 9 F : 16 P : 25");
        moreInfo = new JButton("More nutrition info");




        itemName.setBounds(10,10,140,20);
        portionLabel.setBounds(10,40,180,30);
        calorieLabel.setBounds(10,70,180,30);
        macrosLabel.setBounds(10,100,180,30);


        coords.add(itemName.getX()+","+itemName.getY());
        setSize(160,140);
        setBackground(Color.WHITE);
        setVisible(true);
        setLocation(x,y);
        this.x = x;
        this.y = y;


        add(itemName);
        add(portionLabel);
        add(calorieLabel);
        add(macrosLabel);

    }



}
