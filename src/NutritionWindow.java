import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class NutritionWindow extends JFrame{
    Item item;

    Container content;
    JPanel panel;
    JLabel servingLabel;
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
    JLabel servingSize;
    JLabel calories;
    JLabel carbs;
    JLabel fat;
    JLabel satFat;
    JLabel cholesterol;
    JLabel sodium;
    JLabel sugar;
    JLabel fiber;
    JLabel protein;
    JLabel calcium;
    JLabel iron;

    double multiplier = 1;

    JComboBox multiplierBox;

    JButton servingSizeImage;
    final static Double[] servingUnits = new Double[]{.25,.5,.75,1.0,1.25,1.5,1.75,2.0,2.25,2.5,2.75,3.0,3.25,3.5,3.75,4.0,4.25,4.5,4.75,5.0};


    NutritionWindow(Item item, ActionListener actionListener, ItemListener itemListener,double multiplier){
        super();
        this.item = item;
        setTitle(item.name+" Nutrition");
        setBounds(100,300,300, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        content = getContentPane();
        panel = new JPanel();
        setLayout(new GridLayout(13,2));
        panel.setBounds(100,300,300,500);
        multiplierBox = new JComboBox(servingUnits);
        this.multiplier = multiplier;

        servingLabel= new JLabel("   Serving size: ");
        caloriesLabel = new JLabel("   Calories: ");
        carbsLabel = new JLabel("   Carbs: ");
        fatLabel = new JLabel("   Fat: ");
        satFatLabel = new JLabel("   Saturated fat: ");
        proteinLabel = new JLabel("   Protein:  ");
        fiberLabel = new JLabel("   Fiber: ");
        calciumLabel = new JLabel("   Calcium");
        ironLabel = new JLabel("   Iron");
        sugarLabel = new JLabel("   Sugar: ");
        sodiumLabel = new JLabel("   Sodium: ");
        cholesterolLabel = new JLabel("   Cholesterol");
        servingSize = new JLabel(item.serving);
        servingSizeImage = new JButton("See serving size");

        servingSizeImage.addActionListener(actionListener);
        multiplierBox.addItemListener(itemListener);



        calculate();
        add(servingSizeImage);
        add(multiplierBox);

        add(servingLabel);
        add(servingSize);
        add(caloriesLabel);
        add(calories);
        add(fatLabel);
        add(fat);
        add(satFatLabel);
        add(satFat);
        add(cholesterolLabel);
        add(cholesterol);
        add(carbsLabel);
        add(carbs);
        add(sugarLabel);
        add(sugar);
        add(fiberLabel);
        add(fiber);
        add(proteinLabel);
        add(protein);
        add(calciumLabel);
        add(calcium);
        add(ironLabel);
        add(iron);
        add(sodiumLabel);
        add(sodium);
    }

    public String getDV(double top, double bottom){
        try {
            int i = (int) Math.round(100*top / bottom);
            if(i > 999999 || i < -999){
                return "";
            }return "("+String.valueOf(i)+"% DV)";
        }
            catch(Exception e){
                return"";
            }
        }

        public void calculate(){

            calories = new JLabel(String.valueOf(Math.round(item.calories*multiplier))+" g");
            fat = new JLabel(String.valueOf(item.fat*multiplier)+" g "+getDV(item.fat*multiplier,(double)Main.user.preferences.macros[2]*Main.user.preferences.calorieGoal/900));
            satFat = new JLabel(String.valueOf(item.saturatedFat*multiplier)+" g ");
            cholesterol = new JLabel(String.valueOf(Math.round(item.cholesterol*multiplier))+" mg "+getDV(item.cholesterol*multiplier,Main.user.preferences.cholesterolGoal));
            carbs = new JLabel(String.valueOf(Math.round(item.carbs*multiplier))+" g "+getDV(item.carbs*multiplier,(double)Main.user.preferences.macros[2]*Main.user.preferences.calorieGoal/400));
            sugar = new JLabel(String.valueOf(Math.round(item.sugar*multiplier))+" g");
            fiber =  new JLabel(String.valueOf(Math.round(item.fiber*multiplier))+" g "+getDV(item.fiber*multiplier,Main.user.preferences.fiberGoal));
            protein = new JLabel(String.valueOf(Math.round(item.protein*multiplier))+" g "+getDV(item.protein*multiplier,(double)Main.user.preferences.macros[2]*Main.user.preferences.calorieGoal/400));
            iron = new JLabel(String.valueOf(Math.round(item.iron*multiplier))+" mg "+getDV(item.iron*multiplier,Main.user.preferences.ironGoal));
            calcium = new JLabel(String.valueOf(Math.round(item.calcium*multiplier))+"mg "+getDV(item.calcium*multiplier,Main.user.preferences.calciumGoal));
            sodium = new JLabel(String.valueOf(Math.round(item.sodium*multiplier))+" mg "+getDV(item.sodium*multiplier,Main.user.preferences.sodiumGoal));
        }

    }



