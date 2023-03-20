import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ItemPanel extends JPanel {
    Item item;
    JLabel itemName;
    JLabel portionLabel;
    JLabel calorieLabel;
    JLabel carbsLabel;
    JLabel fatLabel;
    JLabel proteinLabel;
    static JButton moreInfo;
    int x;
    int y;
    ArrayList<String> coords;

    double portionsize;

    String unit;

    double multiplier = 1;

    MouseListener mouseListener;

    boolean restricted;





    ItemPanel(Item item, int x, int y, MouseListener mouseListener, ActionListener actionListener){
        addMouseListener(mouseListener);
        String toConvert = "";
        for(int i = 0; i < item.serving.length(); i ++){
            if(item.serving.charAt(i) < 58){
                toConvert += item.serving.charAt(i);
            }else{
                unit = item.serving.substring(i);
                break;
            }
        }
        try{
            portionsize = Double.parseDouble(toConvert);
        }catch(NumberFormatException e){
            if(toConvert.indexOf('/') > -1){
                portionsize = (int)toConvert.charAt(0) / (int)toConvert.charAt(2);

            }
        }




        coords = new ArrayList<>();
        this.item = item;
        itemName = new JLabel(item.name);
        calorieLabel = new JLabel(item.calories+" calories");
        portionLabel = new JLabel(portionsize*multiplier+" "+unit);
        carbsLabel = new JLabel("C - "+Math.round(item.carbs*multiplier));
        fatLabel = new JLabel("F - "+Math.round(item.fat*multiplier));
        proteinLabel = new JLabel("P - "+Math.round(item.protein*multiplier));
        moreInfo = new JButton("More nutrition info");



        itemName.setBounds(10,10,140,20);
        portionLabel.setBounds(10,30,180,30);
        calorieLabel.setBounds(10,55,180,20);
        carbsLabel.setBounds(10,70,60,30);
        fatLabel.setBounds(63,70,60,30);
        proteinLabel.setBounds(115,70,60,30);
        moreInfo.setBounds(5,100,150,25);
        moreInfo.addActionListener(actionListener);

        carbsLabel.setForeground(Color.GREEN);
        fatLabel.setForeground(Color.RED);
        proteinLabel.setForeground(Color.BLUE);




        coords.add(itemName.getX()+","+itemName.getY());
        setSize(160,140);
        setBackground(Color.WHITE);
        setVisible(true);
        setLocation(x,y);
        this.x = x;
        this.y = y;

        if (Main.user.preferences.isVegetarian&&!item.isVegetarian||(Main.user.preferences.isVegan&&!item.isVegan)||(Main.user.preferences.isGlutenFree&& !item.hasGluten)||(Main.user.preferences.isDairyFree&&!item.hasDairy)){
            setBackground(new Color(230,200,200));
            moreInfo.setBackground(Color.lightGray);
            restricted = true;
        }

        for(int i = 1; i < Main.user.preferences.allergies.size(); i++){
            for(int j = 0; j < item.ingredients.length; j++){
                if(item.ingredients[j].contains(Main.user.preferences.allergies.get(i))){
                    setBackground(new Color(230,200,200));
                    moreInfo.setBackground(Color.lightGray);
                    restricted = true;
                    break;
                }
            }
        }


        add(itemName);
        add(portionLabel);
        add(calorieLabel);
        add(carbsLabel);
        add(fatLabel);
        add(proteinLabel);
        add(moreInfo);

    }



}
