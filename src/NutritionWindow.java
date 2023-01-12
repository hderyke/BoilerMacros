import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    JTextField multiplierField;

    JButton servingSizeMultiplier;


    NutritionWindow(Item item, ActionListener actionListener){
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
        multiplierField = new JTextField();

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
        servingSizeMultiplier = new JButton("Multiply serving size");

        servingSizeMultiplier.addActionListener(actionListener);


        calculate();
        add(servingSizeMultiplier);
        add(multiplierField);

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
            return String.valueOf(Math.round(100*top / bottom));
        }
            catch(Exception e){
                return"--";
            }
        }

        public void calculate(){
            calories = new JLabel(String.valueOf(Math.round(item.calories*multiplier)));
            fat = new JLabel(String.valueOf(item.fat*multiplier)+" g ("+Math.round(item.fat*multiplier/Main.user.getFat()*100)+"% DV)");
            satFat = new JLabel(String.valueOf(item.saturatedFat*multiplier)+" g ");
            cholesterol = new JLabel(String.valueOf(Math.round(item.cholesterol*multiplier))+" mg ("+getDV(item.cholesterol*multiplier,Main.user.getCholesterol())+"% DV)");
            carbs = new JLabel(String.valueOf(Math.round(item.carbs*multiplier))+" g ("+getDV(item.carbs*multiplier,Main.user.getCarbs())+"% DV)");
            sugar = new JLabel(String.valueOf(Math.round(item.sugar*multiplier))+" g");
            fiber =  new JLabel(String.valueOf(Math.round(item.fiber*multiplier))+" g ("+getDV(item.fiber*multiplier,Main.user.getFiber())+"% DV)");
            protein = new JLabel(String.valueOf(Math.round(item.protein*multiplier))+" g ("+getDV(item.protein*multiplier,Main.user.getProtein())+"% DV)");
            iron = new JLabel(String.valueOf(Math.round(item.iron*multiplier))+" mg ("+getDV(item.iron*multiplier,Main.user.getIron())+"% DV)");
            calcium = new JLabel(String.valueOf(Math.round(item.calcium*multiplier))+" mg ("+getDV(item.cholesterol*multiplier,Main.user.getCholesterol())+"% DV)");
            sodium = new JLabel(String.valueOf(Math.round(item.sodium*multiplier))+" mg ("+getDV(item.sodium*multiplier,Main.user.getCholesterol())+"% DV)");
        }

    }



