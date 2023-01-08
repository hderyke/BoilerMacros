import javax.swing.*;
import java.awt.*;

public class NutritionWindow extends JFrame{

    Container content;
    JPanel panel;
    JLabel caloriesLabel;
    JLabel carbsLabel;
    JLabel fatLabel;
    JLabel proteinLabel;
    JLabel satFatLabel;
    JLabel fiberLabel;
    JLabel calciumLabel;
    JLabel ironLabel;
    JLabel sugarLabel;
    JLabel sodiumLabel;
    JLabel cholesterolLabel;


    NutritionWindow(Item item){
        super();
        setBounds(100,300,300, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        content = getContentPane();
        panel = new JPanel();
        setLayout(new GridLayout(10,1));
        panel.setLayout(new GridLayout(10,1));
        panel.setBounds(100,300,300,500);
        add(panel);


        caloriesLabel = new JLabel("Calories: ");
        carbsLabel = new JLabel("Carbs: ");
        fatLabel = new JLabel("Fat: ");
        satFatLabel = new JLabel("Saturated fat: ");
        proteinLabel = new JLabel("Protein:  ");
        fiberLabel = new JLabel("Fiber: ");
        calciumLabel = new JLabel("Calcium");
        ironLabel = new JLabel("Iron");
        sugarLabel = new JLabel("Sugar: ");
        sodiumLabel = new JLabel("Sodium: ");
        cholesterolLabel = new JLabel("Cholesterol");


        add(caloriesLabel);
        add(carbsLabel);
        add(fatLabel);
        add(satFatLabel);
        add(proteinLabel);
        add(fiberLabel);
        add(calciumLabel);
        add(ironLabel);
        add(sugarLabel);
        add(sodiumLabel);
        add(cholesterolLabel);
    }


}
