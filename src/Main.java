import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main extends JComponent implements Runnable{

    //GUI stuff
    JFrame frame;
    Container content;

    public static final String[] entreeKeywords = new String[]{"eggs","chicken","beef","pork","pasta","pizza","sausage","burger","patty","fish","shrimp","crab","turkey"};
    public static final String[] diningHalls = new String[]{"Wiley","Earhart","Ford","Windsor","Hillenbrand"};
    ActionListener actionListener = new ActionListener() {


        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource().equals(preferencesButton)){

                content.removeAll();
                frame.repaint();
                preferencesScreen();
                content.add(preferencesScreen,BorderLayout.CENTER);

            }

            else if(e.getSource().equals(settingsButton)){
                writeFiles();
            }

            else if(e.getSource().equals(meal1)){
                content.removeAll();
                content.repaint();
                mealScreen(1, (String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[0]));
                content.add(mealScreen);
            }else if(e.getSource().equals(meal2)){
                content.removeAll();
                mealScreen(2,(String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[0]));
                content.add(mealScreen);
                content.repaint();
            }else if(e.getSource().equals(meal3)){
                content.removeAll();
                mealScreen(3,(String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[0]));
                content.add(mealScreen);
                content.repaint();
            }

            else if(e.getSource().equals(backButton)){
                if(backButton.getParent().equals(preferencesScreen)) {

                    user.preferences.portions = (double) slider.getValue() / 100;
                    if (vegetarianBox.isSelected()) {
                        user.preferences.isVegetarian = true;
                    } else {
                        user.preferences.isVegetarian = false;
                    }
                    if (veganBox.isSelected()) {
                        user.preferences.isVegan = true;
                    } else {
                        user.preferences.isVegan = false;
                    }
                    if (glutenBox.isSelected()) {
                        user.preferences.isGlutenFree = true;
                    } else {
                        user.preferences.isGlutenFree = false;
                    }
                    if (dairyBox.isSelected()) {
                        user.preferences.isDairyFree = true;
                    } else {
                        user.preferences.isDairyFree = false;
                    }
                    if (user.preferences.macros[0] + user.preferences.macros[1] + user.preferences.macros[2] != 100) {
                        JOptionPane.showMessageDialog(preferencesScreen, "Macro ratio must add up to 100", "BoilerMacros", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                writeFiles();
                content.removeAll();
                frame.repaint();
                mainScreen();
                content.add(mainScreen);
            }

            else if(e.getSource().equals(addFiberGoal)){
                preferencesScreen.repaint();
                fiberGoalField.setText("");
                fiberGoalField.setBackground(Color.BLACK);
                preferencesScreen.remove(addFiberGoal);
                preferencesScreen.add(fiberGoal);
                preferencesScreen.add(fiberGoalField);

        }

            else if(e.getSource().equals(addCalciumGoal)){
            preferencesScreen.repaint();
            calciumGoalField.setText("");
            calciumGoal.setBackground(Color.WHITE);
            preferencesScreen.remove(addCalciumGoal);
            preferencesScreen.add(calciumGoal);
            preferencesScreen.add(calciumGoalField);

        }

            else if(e.getSource().equals(addIronGoal)){
                preferencesScreen.repaint();
                ironGoalField.setText("");
                ironGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addIronGoal);
                preferencesScreen.add(ironGoal);
                preferencesScreen.add(ironGoalField);

            }

            else if(e.getSource().equals(addSugarGoal)){
                preferencesScreen.repaint();
                sugarGoalField.setText("");
                sugarGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addSugarGoal);
                preferencesScreen.add(sugarGoal);
                preferencesScreen.add(sugarGoalField);

            }

            else if(e.getSource().equals(addSodiumGoal)){
                preferencesScreen.repaint();
                sodiumGoalField.setText("");
                sodiumGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addSodiumGoal);
                preferencesScreen.add(sodiumGoal);
                preferencesScreen.add(sodiumGoalField);

            }
            else if(e.getSource().equals(addCholesterolGoal)){
                preferencesScreen.repaint();
                cholesterolGoalField.setText("");
                cholesterolGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addCholesterolGoal);
                preferencesScreen.add(cholesterolGoal);
                preferencesScreen.add(cholesterolGoalField);

            }

            if(e.getSource() instanceof ItemPanel){
                System.out.println(((ItemPanel) e.getSource()).item.calories);
            }


        }




    };

    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource().equals(unitSwitcher)){
                preferencesScreen.remove(units);
                System.out.println(unitSwitcher.getSelectedIndex());
                units.setText(new String[]{"%","g"}[unitSwitcher.getSelectedIndex()]);
                if(units.getText().equals("g")&&e.getStateChange()==2){
                    unitConverter(carbRatio);
                    unitConverter(fatRatio);
                    unitConverter(proteinRatio);

                }else if(e.getStateChange()==2){
                    carbRatio.setText(String.valueOf(user.preferences.macros[0]));
                    fatRatio.setText(String.valueOf(user.preferences.macros[1]));
                    proteinRatio.setText(String.valueOf(user.preferences.macros[2]));
                }
                preferencesScreen.add(units);
                content.repaint();
            }

        }
    };

    FocusListener focusLister = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
                e.getComponent().setBackground(Color.WHITE);
                content.repaint();

        }

        @Override
        public void focusLost(FocusEvent e) {
            if(!e.getComponent().equals(carbRatio)&&!e.getComponent().equals(fatRatio)&& !e.getComponent().equals(proteinRatio)) {
                e.getComponent().setBackground(grey);
            }

            if(e.getSource().equals(caloriesGoalField)){
                user.preferences.calorieGoal = (Integer.parseInt(caloriesGoalField.getText()));
                int index = unitSwitcher.getSelectedIndex();
                content.remove(preferencesScreen);
                preferencesScreen();
                unitSwitcher.setSelectedIndex(index);
                itemListener.itemStateChanged(new ItemEvent(unitSwitcher,ItemEvent.ITEM_STATE_CHANGED,unitSwitcher.getSelectedItem(), ItemEvent.DESELECTED));
                content.add(preferencesScreen);
            }else if(e.getSource().equals(fiberGoalField)){
                user.preferences.fiberGoal = Integer.parseInt(fiberGoalField.getText());
            }else if(e.getSource().equals(calciumGoalField)){
                user.preferences.calciumGoal = Integer.parseInt(calciumGoalField.getText());
            }else if(e.getSource().equals(ironGoalField)){
                user.preferences.ironGoal = Integer.parseInt(ironGoalField.getText());
            }else if(e.getSource().equals(sugarGoalField)){
                user.preferences.sugarGoal = Integer.parseInt(sugarGoalField.getText());
            }else if(e.getSource().equals(sodiumGoalField)){
                user.preferences.sodiumGoal = Integer.parseInt(cholesterolGoalField.getText());
            }else if(e.getSource().equals(cholesterolGoalField)){
                user.preferences.cholesterolGoal = Integer.parseInt(cholesterolGoalField.getText());
            }

            content.repaint();

        }
    };

    KeyListener keyListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {


        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 10) {//enter key
                if (e.getSource().equals(caloriesGoalField)) {
                    try{user.preferences.calorieGoal = Integer.parseInt(caloriesGoalField.getText());
                        int index = unitSwitcher.getSelectedIndex();
                        content.remove(preferencesScreen);
                        preferencesScreen();
                        unitSwitcher.setSelectedIndex(index);
                        itemListener.itemStateChanged(new ItemEvent(unitSwitcher,ItemEvent.ITEM_STATE_CHANGED,unitSwitcher.getSelectedItem(), ItemEvent.DESELECTED));
                        content.add(preferencesScreen);


                    }catch (Exception f){
                        JOptionPane.showMessageDialog(preferencesScreen,"Please enter a valid number.","BoilerMacros",JOptionPane.ERROR_MESSAGE);


                    }

                }
                if (e.getSource().equals(carbRatio)) {
                    try {
                        if(units.getText().equals("g")){
                            convertByMacros(carbRatio);
                            return;
                        }
                        user.preferences.macros[0] = Integer.parseInt(carbRatio.getText());
                        if(user.preferences.macros[0]+user.preferences.macros[1]+user.preferences.macros[2] != 100){
                            JOptionPane.showMessageDialog(preferencesScreen,"Macro ratio must add up to 100","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        preferencesScreen.remove(carbRatio);
                        carbRatio.setBackground(grey);
                        preferencesScreen.add(carbRatio);
                        preferencesScreen.repaint();
                    } catch (Exception f) {
                        if (!carbRatio.getText().equals("")) {
                            JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                        }else{
                            user.preferences.calciumGoal = -1;
                        }

                    }

                }
                if (e.getSource().equals(fatRatio)) {
                    try {
                        if(units.getText().equals("g")){
                            convertByMacros(fatRatio);
                            return;
                        }
                        user.preferences.macros[1] = Integer.parseInt(fatRatio.getText());
                        if(user.preferences.macros[0]+user.preferences.macros[1]+user.preferences.macros[2] != 100){
                            JOptionPane.showMessageDialog(preferencesScreen,"Macro ratio must add up to 100","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        preferencesScreen.remove(fatRatio);
                        fatRatio.setBackground(grey);
                        preferencesScreen.add(fatRatio);
                        preferencesScreen.repaint();
                    } catch (Exception f) {
                        if (!fatRatio.getText().equals("")) {
                            JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                        }else{
                            user.preferences.calciumGoal = -1;
                        }

                    }

                }
                if (e.getSource().equals(proteinRatio)) {
                    try {
                        if(units.getText().equals("g")){
                            convertByMacros(proteinRatio);
                            return;
                        }
                        user.preferences.macros[2] = Integer.parseInt(proteinRatio.getText());
                        if(user.preferences.macros[0]+user.preferences.macros[1]+user.preferences.macros[2] != 100){
                            JOptionPane.showMessageDialog(preferencesScreen,"Macro ratio must add up to 100","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        preferencesScreen.remove(proteinRatio);
                        proteinRatio.setBackground(grey);
                        preferencesScreen.add(proteinRatio);
                        preferencesScreen.repaint();
                    } catch (Exception f) {
                        if (!calciumGoalField.getText().equals("")) {
                            JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                        }else{
                            user.preferences.calciumGoal = -1;
                        }

                    }

                }
                if (e.getSource().equals(restrictionDropdown)) {

                }
                if (e.getSource().equals(fiberGoalField)) {
                    try {
                        user.preferences.fiberGoal = Integer.parseInt(fiberGoalField.getText());
                        preferencesScreen.remove(fiberGoalField);
                        fiberGoalField.setBackground(grey);
                        preferencesScreen.add(fiberGoalField);
                        preferencesScreen.repaint();
                    } catch (Exception f) {
                        if (!fiberGoalField.getText().equals("")) {
                            JOptionPane.showMessageDialog(preferencesScreen, "Invalid input!", "BoilerMacros", JOptionPane.ERROR_MESSAGE);
                        } else {
                            user.preferences.fiberGoal = -1;
                        }
                    }

                    }

                if (e.getSource().equals(calciumGoalField)) {
                    try {
                        user.preferences.calciumGoal = Integer.parseInt(calciumGoalField.getText());
                        preferencesScreen.remove(calciumGoalField);
                        calciumGoalField.setBackground(grey);
                        preferencesScreen.add(calciumGoalField);
                        preferencesScreen.repaint();
                    } catch (Exception f) {
                        if (!calciumGoalField.getText().equals("")) {
                            JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                        }else{
                            user.preferences.calciumGoal = -1;
                        }

                    }
                }
                    if (e.getSource().equals(ironGoalField)) {
                        try {
                            user.preferences.ironGoal = Integer.parseInt(ironGoalField.getText());
                            preferencesScreen.remove(ironGoalField);
                            ironGoalField.setBackground(grey);
                            preferencesScreen.add(ironGoalField);
                            preferencesScreen.repaint();
                        } catch (Exception f) {
                            if (!ironGoalField.getText().equals("")) {
                                JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            }else{
                                user.preferences.ironGoal = -1;
                            }

                        }
                    }
                    if (e.getSource().equals(sugarGoalField)) {
                        try {
                            user.preferences.sugarGoal = Integer.parseInt(sugarGoalField.getText());
                            preferencesScreen.remove(sugarGoalField);
                            sugarGoalField.setBackground(grey);
                            preferencesScreen.add(sugarGoalField);
                            preferencesScreen.repaint();
                        } catch (Exception f) {
                            if (!sugarGoalField.getText().equals("")) {
                                JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            }else{
                                user.preferences.sugarGoal = -1;
                            }

                        }
                    }
                    if (e.getSource().equals(sodiumGoalField)) {
                        try {
                            user.preferences.sodiumGoal = Integer.parseInt(sodiumGoalField.getText());
                            preferencesScreen.remove(sodiumGoalField);
                            sodiumGoalField.setBackground(grey);
                            preferencesScreen.add(sodiumGoalField);
                            preferencesScreen.repaint();
                        } catch (Exception f) {
                            if (!sodiumGoalField.getText().equals("")) {
                                JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            }else{
                                user.preferences.sodiumGoal = -1;
                            }

                        }
                    }
                    if (e.getSource().equals(cholesterolGoalField)) {
                        try {
                            user.preferences.cholesterolGoal = Integer.parseInt(cholesterolGoalField.getText());
                            preferencesScreen.remove(cholesterolGoalField);
                            cholesterolGoalField.setBackground(grey);
                            preferencesScreen.add(cholesterolGoalField);
                            preferencesScreen.repaint();
                        } catch (Exception f) {
                            if (!cholesterolGoalField.getText().equals("")) {
                                JOptionPane.showMessageDialog(preferencesScreen,"Invalid input!","BoilerMacros",JOptionPane.ERROR_MESSAGE);
                            }else{
                                user.preferences.cholesterolGoal = -1;
                            }

                        }
                    }




            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }


    };

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {



        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource().equals(slider)){
            for(Component component: content.getComponents()){
                if(component instanceof ItemPanel) {//moves all the buttons
                    component.setLocation(((ItemPanel) component).x, ((ItemPanel) component).y - slider.getValue());
                }
                }
            }
            mealScreen.setLocation(0,0-slider.getValue());
            content.repaint();


        }
    };



    //main screen
    JPanel mainScreen;
    JLabel title;
    JButton meal1;
    JButton meal2;
    JButton meal3;
    JButton preferencesButton;
    JButton settingsButton;
    JLabel calories;
    JLabel carbs;
    JLabel fat;
    JLabel protein;
    JLabel fiber;
    JLabel calcium;
    JLabel iron;
    JLabel sugar;
    JLabel sodium;
    JLabel cholesterol;



    //preferences screen

    JPanel preferencesScreen;
    JLabel preferencesHeader;
    JButton backButton;
    JLabel portionSize;
    JSlider slider;
    JLabel calorieGoal;
    JTextField caloriesGoalField;
    JLabel macrosLabel;
    JLabel carbLabel;
    JLabel fatLabel;
    JLabel proteinLabel;
    JTextField carbRatio;
    JTextField fatRatio;
    JTextField proteinRatio;
    JLabel nutritionGoalsLabel;
    JLabel restrictionsLabel;
    JLabel units;
    String[] unitArr;
    JComboBox<String> restrictionDropdown;

    JTextField restrictionsField;
    JComboBox<String> unitSwitcher;
    JCheckBox vegetarianBox;
    JCheckBox veganBox;
    JCheckBox glutenBox;
    JCheckBox dairyBox;
    JLabel fiberGoal;
    JLabel calciumGoal;
    JLabel ironGoal;
    JLabel sugarGoal;
    JLabel sodiumGoal;
    JLabel cholesterolGoal;
    JTextField fiberGoalField;
    JTextField calciumGoalField;
    JTextField ironGoalField;
    JTextField sugarGoalField;
    JTextField sodiumGoalField;
    JTextField cholesterolGoalField;
    JButton addFiberGoal;
    JButton addCalciumGoal;
    JButton addIronGoal;
    JButton addSugarGoal;
    JButton addSodiumGoal;
    JButton addCholesterolGoal;
    Color grey;

    //meal screen

    JPanel mealScreen;

    JLabel mealHeader;
    JPanel totalsPanel;
    JTextField searchBar;
    JLabel EntreeHeader;
    JLabel sidesHeader;
    JLabel desertsHeader;
    JLabel drinksLabel;
    JLabel diningHall;
    JPanel entreePanel;
    JPanel sidesPanel;
    JPanel drinksPanel;
    JPanel desertsPanel;

    JLabel totalMealCalories;
    JLabel totalMealCarbs;
    JLabel totalMealFat;
    JLabel totalMealProtein;
    JPanel itemListPanel;
    JButton moreNutrition;

    ArrayList<ItemPanel> itemPanelArr;
    ArrayList<JLabel> itemLabels;






    public static ArrayList<Item> items = new ArrayList<>();
    static User user;

    public static void main(String[] args) {
        try {
            //read user info
            BufferedReader userReader = new BufferedReader(new FileReader("storage/UserInfo.txt"));
            String name = userReader.readLine();
            String preferences = userReader.readLine();
            String meals = userReader.readLine();
            userReader.close();

            //adding food to item list
            BufferedReader listedReader = new BufferedReader(new FileReader("storage/fooditems/listedfoods.txt"));
            while (true) {
                String line = listedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] itemArr = line.split("\\,");
                items.add(new Item(itemArr[0], itemArr[1],Integer.parseInt(itemArr[2]),
                        Double.parseDouble(itemArr[3]), Double.parseDouble(itemArr[4]), Integer.parseInt(itemArr[5]),
                        Integer.parseInt(itemArr[6]), Integer.parseInt(itemArr[7]),
                        Integer.parseInt(itemArr[8]), Integer.parseInt(itemArr[9]), Integer.parseInt(itemArr[10]),
                        Integer.parseInt(itemArr[11]), Integer.parseInt(itemArr[12]),itemArr[13],itemArr[14],itemArr[15]));
            }
            listedReader.close();
            user = new User(name, preferences, meals);
            SwingUtilities.invokeLater(new Main());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        frame = new JFrame("BoilerMacros");
        frame.setSize(500, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        content = frame.getContentPane();
        mainScreen();
        content.add(mainScreen,BorderLayout.CENTER);

    }



    public void mainScreen() {
        mainScreen = new JPanel();
        mainScreen.setLayout(null);
        mainScreen.setBounds(0,0,500,800);
        frame.setVisible(true);

        String greeting = "";

        int hour = Integer.parseInt(String.valueOf(LocalTime.now()).substring(0, 2));

        if (hour < 12) {
            greeting = "Good morning, " + user.name;
        } else if (hour < 18) {
            greeting = "Good afternoon, " + user.name;
        } else {
            greeting = "Good evening, " + user.name;
        }

       //buttons
        meal1 = new JButton("FirstMeal");
        meal2 = new JButton("Second meal");
        meal3 = new JButton("Third meal");
        preferencesButton = new JButton("...");
        settingsButton = new JButton("Settings");


        //text fields
        calories = new JLabel(user.getCalories()+" kcals");
        carbs = new JLabel(user.getCarbs()+"g carbs");
        fat = new JLabel(user.getFat()+"g fat");
        protein = new JLabel(user.getProtein()+"g protein");
        fiber = new JLabel("Fiber: "+user.getFiber()+"g");
        calcium = new JLabel("Calcium: "+user.getCalcium()+"% DV");
        iron = new JLabel("Iron: "+user.getIron()+"%DV");
        sugar = new JLabel("Sugar: "+user.getSugar()+"g");
        sodium = new JLabel("Sodium: "+user.getSodium()+"mg");
        cholesterol = new JLabel("Cholesterol: "+user.getCholesterol()+"mg");
        title = new JLabel(greeting + "."); //rename later


        meal1.setLocation(60,450);
        meal2.setLocation(60,550);
        meal3.setLocation(60,650);
        preferencesButton.setBounds(400,20,70,40);
        settingsButton.setBounds(50,20,70,40);
        calories.setBounds(230,100,200,100);
        carbs.setBounds(110,160,110,110);
        fat.setBounds(220,160,170,110);
        protein.setBounds(330,160,110,110);
        fiber.setBounds(70,290,170,50);
        calcium.setBounds(70,330,200,50);
        iron.setBounds(70,370,200,50);
        sugar.setBounds(280,290,170,50);
        sodium.setBounds(280,330,170,50);
        cholesterol.setBounds(280,370,220,50);

        preferencesButton.setFont(new Font("Serif", Font.BOLD, 15));
        calories.setFont(new Font("Serif", Font.BOLD, 20));
        carbs.setFont(new Font("Serif", Font.BOLD, 20));
        fat.setFont(new Font("Serif", Font.BOLD, 20));
        protein.setFont(new Font("Serif", Font.BOLD, 20));

        fiber.setFont(new Font("Serif", Font.BOLD, 20));
        calcium.setFont(new Font("Serif", Font.BOLD, 20));
        iron.setFont(new Font("Serif", Font.BOLD, 20));
        sugar.setFont(new Font("Serif", Font.BOLD, 20));
        sodium.setFont(new Font("Serif", Font.BOLD, 20));
        cholesterol.setFont(new Font("Serif", Font.BOLD, 20));

        meal1.setSize(350,90);
        meal2.setSize(350,90);
        meal3.setSize(350,90);




        meal1.addActionListener(actionListener);
        meal1.setBackground(Color.BLUE);
        meal1.setForeground(Color.BLACK);
        meal2.addActionListener(actionListener);
        meal2.setBackground(Color.BLUE);
        meal2.setForeground(Color.RED);
        meal3.addActionListener(actionListener);
        meal3.setBackground(Color.BLUE);
        meal3.setForeground(Color.PINK);
        preferencesButton.addActionListener(actionListener);
        settingsButton.addActionListener(actionListener);

        mainScreen.add(meal1);
        mainScreen.add(meal2);
        mainScreen.add(meal3);
        mainScreen.add(preferencesButton);
        mainScreen.add(settingsButton);
        mainScreen.add(carbs);
        mainScreen.add(fat);
        mainScreen.add(protein);
        mainScreen.add(calories);
        mainScreen.add(fiber);
        mainScreen.add(calcium);
        mainScreen.add(iron,BorderLayout.PAGE_END);
        mainScreen.add(sugar,BorderLayout.PAGE_START);
        mainScreen.add(sodium);
        mainScreen.add(cholesterol);

        mainScreen.add(title);

        //greeting
        title.setFont(new Font("Serif", Font.BOLD, 34));//good day sir
        title.setSize(550, 175);
        title.setLocation(50, 20);


        content.add(mainScreen);

    }

    public void preferencesScreen(){
        preferencesScreen = new JPanel();
        preferencesScreen.setLayout(new FlowLayout());
        preferencesScreen.setBounds(0,0,500,1000);
        unitArr = new String[]{"%","g"};

        preferencesHeader = new JLabel("Preferences");
        backButton = new JButton("<-");
        slider = new JSlider(JSlider.HORIZONTAL,0,100,(int)(user.preferences.portions*100));
        restrictionDropdown = new JComboBox<>(user.getPreferences().allergies);
        restrictionsField = new JTextField();
        portionSize = new JLabel("Portion Size:");
        calorieGoal = new JLabel("Calorie goal: ");
        caloriesGoalField = new JTextField(String.valueOf(user.getPreferences().calorieGoal));
        macrosLabel = new JLabel("Macros");
        carbLabel = new JLabel("Carbohydrates");
        fatLabel = new JLabel("Fat");
        proteinLabel = new JLabel("Protein");
        carbRatio = new JTextField(String.valueOf(user.getPreferences().macros[0]));
        fatRatio = new JTextField(String.valueOf(user.getPreferences().macros[1]));
        proteinRatio = new JTextField(String.valueOf(user.getPreferences().macros[2]));
        unitSwitcher = new JComboBox<>(unitArr);
        units = new JLabel("%");
        nutritionGoalsLabel = new JLabel("Nutrition goals");
        restrictionsLabel = new JLabel("Restrictions/Allergies");
        fiberGoal = new JLabel("Fiber:                           g");
        fiberGoalField = new JTextField(String.valueOf(user.getPreferences().fiberGoal));
        calciumGoal = new JLabel("Calcium:                       mg");
        calciumGoalField = new JTextField(String.valueOf(user.getPreferences().calciumGoal));
        ironGoal = new JLabel("Iron:                            mg");
        ironGoalField = new JTextField(String.valueOf(user.getPreferences().ironGoal));
        sugarGoal = new JLabel("Sugar:                    g");
        sugarGoalField = new JTextField(String.valueOf(user.getPreferences().sugarGoal));
        sodiumGoal = new JLabel("Sodium:                  mg ");
        sodiumGoalField= new JTextField(String.valueOf(user.getPreferences().sodiumGoal));
        cholesterolGoal = new JLabel("Cholesterol:                 mg");
        cholesterolGoalField = new JTextField(String.valueOf(user.getPreferences().cholesterolGoal));
        vegetarianBox = new JCheckBox("Vegetarian");
        veganBox = new JCheckBox("Vegan");
        glutenBox = new JCheckBox("Gluten");
        dairyBox = new JCheckBox("Dairy");
        grey = new Color(190,190,190);


        unitSwitcher.addActionListener(actionListener);
        restrictionDropdown.addActionListener(actionListener);
        unitSwitcher.addItemListener(itemListener);
        backButton.addActionListener(actionListener);
        caloriesGoalField.addMouseListener(mouseListener);
        fiberGoalField.addMouseListener(mouseListener);
        calciumGoalField.addMouseListener(mouseListener);
        ironGoalField.addMouseListener(mouseListener);
        sugarGoalField.addMouseListener(mouseListener);
        sodiumGoalField.addMouseListener(mouseListener);
        ironGoalField.addMouseListener(mouseListener);
        cholesterolGoalField.addMouseListener(mouseListener);

        caloriesGoalField.addFocusListener(focusLister);
        carbRatio.addFocusListener(focusLister);
        fatRatio.addFocusListener(focusLister);
        proteinRatio.addFocusListener(focusLister);
        fiberGoalField.addFocusListener(focusLister);
        calciumGoalField.addFocusListener(focusLister);
        ironGoalField.addFocusListener(focusLister);
        sugarGoalField.addFocusListener(focusLister);
        sodiumGoalField.addFocusListener(focusLister);
        cholesterolGoalField.addFocusListener(focusLister);

        caloriesGoalField.addKeyListener(keyListener);
        carbRatio.addKeyListener(keyListener);
        fatRatio.addKeyListener(keyListener);
        proteinRatio.addKeyListener(keyListener);
        restrictionDropdown.addKeyListener(keyListener);
        fiberGoalField.addKeyListener(keyListener);
        calciumGoalField.addKeyListener(keyListener);
        ironGoalField.addKeyListener(keyListener);
        sugarGoalField.addKeyListener(keyListener);
        sodiumGoalField.addKeyListener(keyListener);
        cholesterolGoalField.addKeyListener(keyListener);

        //caloriesGoalField.setText(String.valueOf(user.getPreferences().calorieGoal));
        restrictionDropdown.setEditable(true);

        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);

        portionSize.setFont(new Font("Serif", Font.BOLD, 33));
        preferencesHeader.setFont(new Font("Serif", Font.BOLD, 45));
        calorieGoal.setFont(new Font("Serif", Font.BOLD, 33));
        macrosLabel.setFont(new Font("Serif", Font.BOLD, 30));
        nutritionGoalsLabel.setFont(new Font("Serif", Font.BOLD, 30));
        restrictionsLabel.setFont(new Font("Serif", Font.BOLD, 30));

        if(user.getPreferences().fiberGoal > -1 ){
            preferencesScreen.add(fiberGoal);
            preferencesScreen.add(fiberGoalField);


        }else{
            addFiberGoal = new JButton("+ Add fiber goal");
            addFiberGoal.setBounds(90,470,140,20);
            addFiberGoal.addActionListener(actionListener);
            preferencesScreen.add(addFiberGoal);

        }
        if(user.getPreferences().calciumGoal > -1 ){
            preferencesScreen.add(calciumGoal);
            preferencesScreen.add(calciumGoalField);


        }else{
            addCalciumGoal = new JButton("+ Add calcium goal");
            addCalciumGoal.setBounds(90,495,140,20);
            addCalciumGoal.addActionListener(actionListener);
            preferencesScreen.add(addCalciumGoal);

        }
        if(user.getPreferences().ironGoal > -1 ){
            preferencesScreen.add(ironGoal);
            preferencesScreen.add(ironGoalField);


        }else{
            addIronGoal = new JButton("+ Add iron goal");
            addIronGoal.setBounds(90,520,140,20);
            addIronGoal.addActionListener(actionListener);
            preferencesScreen.add(addIronGoal);

        }
        if(user.getPreferences().sugarGoal > -1 ){
            preferencesScreen.add(sugarGoal);
            preferencesScreen.add(sugarGoalField);


        }else{
            addSugarGoal = new JButton("+ Add sugar goal");
            addSugarGoal.setBounds(270,470,150,20);
            addSugarGoal.addActionListener(actionListener);
            preferencesScreen.add(addSugarGoal);

        }
        if(user.getPreferences().sodiumGoal > -1 ){
            preferencesScreen.add(sodiumGoal);
            preferencesScreen.add(sodiumGoalField);


        }else{
            addSodiumGoal = new JButton("+ Add sodium goal");
            addSodiumGoal.setBounds(270,495,140,20);
            addSodiumGoal.addActionListener(actionListener);
            preferencesScreen.add(addSodiumGoal);

        }
        if(user.getPreferences().cholesterolGoal > -1 ){
            preferencesScreen.add(cholesterolGoal);
            preferencesScreen.add(cholesterolGoalField);


        }else{
            addCholesterolGoal = new JButton("+ Add cholesterol goal");
            addCholesterolGoal.setBounds(250,520,140,20);
            addCholesterolGoal.addActionListener(actionListener);
            preferencesScreen.add(addCholesterolGoal);

        }


        preferencesHeader.setBounds(150,20,300,70);
        backButton.setBounds(20,30,50,40);
        slider.setBounds(300,180,160,50);
        portionSize.setBounds(70,150,300,100);
        restrictionsField.setLocation(90,620);
        restrictionsField.setSize(100,20);
        restrictionDropdown.setLocation(90,640);
        restrictionDropdown.setSize(100,30);
        vegetarianBox.setBounds(240,630,140,20);
        veganBox.setBounds(370,630,70,20);
        glutenBox.setBounds(240,690,90,20);
        dairyBox.setBounds(370,690,70,20);
        calorieGoal.setBounds(70, 110,200,60);
        caloriesGoalField.setBounds(300,110,100,40);
        carbRatio.setBounds(120,380,40,30);
        fatRatio.setBounds(240,380,40,30);
        proteinRatio.setBounds(350,380,40,30);
        macrosLabel.setBounds(220,230,200,60);
        carbLabel.setBounds(110,330,100,20);
        fatLabel.setBounds(250,330,60,20);
        proteinLabel.setBounds(350,330,60,20);
        unitSwitcher.setLocation(430,380);
        unitSwitcher.setSize(50,20);
        units.setBounds(430,380,50,20);
        nutritionGoalsLabel.setBounds(150,420,200,50);
        restrictionsLabel.setBounds(100,570,350,50);
        fiberGoal.setBounds(90,470,180,20);
        fiberGoalField.setBounds(170,470,60,20);
        calciumGoal.setBounds(90,495,180,20);
        calciumGoalField.setBounds(170,495,60,20);
        ironGoal.setBounds(90,520,180,20);
        ironGoalField.setBounds(170,520,60,20);
        sugarGoal.setBounds(290,470,180,20);
        sugarGoalField.setBounds(350,470,60,20);
        sodiumGoal.setBounds(290,495,180,20);
        sodiumGoalField.setBounds(350,495,60,20);
        cholesterolGoal.setBounds(270,520,180,20);
        cholesterolGoalField.setBounds(350,520,60,20);

        if(user.preferences.isVegetarian){
            vegetarianBox.setSelected(true);
        }if(user.preferences.isVegan){
            veganBox.setSelected(true);
        }if(user.preferences.isGlutenFree){
            glutenBox.setSelected(true);
        }if(user.preferences.isDairyFree){
            dairyBox.setSelected(true);
        }


        caloriesGoalField.setBackground(grey);
        carbRatio.setBackground(grey);
        fatRatio.setBackground(grey);
        proteinRatio.setBackground(grey);
        protein.setBackground(grey);
        fiberGoalField.setBackground(grey);
        calciumGoalField.setBackground(grey);
        ironGoalField.setBackground(grey);
        sugarGoalField.setBackground(grey);
        sodiumGoalField.setBackground(grey);
        cholesterolGoalField.setBackground(grey);

        preferencesScreen.add(preferencesHeader);
        preferencesScreen.add(backButton);
        preferencesScreen.add(slider);
        preferencesScreen.add(restrictionDropdown);
        preferencesScreen.add(restrictionsField);
        preferencesScreen.add(portionSize);
        preferencesScreen.add(calorieGoal);
        preferencesScreen.add(caloriesGoalField);
        preferencesScreen.add(carbRatio);
        preferencesScreen.add(fatRatio);
        preferencesScreen.add(proteinRatio);
        preferencesScreen.add(macrosLabel);
        preferencesScreen.add(carbLabel);
        preferencesScreen.add(fatLabel);
        preferencesScreen.add(proteinLabel);
        preferencesScreen.add(unitSwitcher);
        preferencesScreen.add(units);
        preferencesScreen.add(nutritionGoalsLabel);
        preferencesScreen.add(restrictionsLabel);
        preferencesScreen.add(vegetarianBox);
        preferencesScreen.add(veganBox);
        preferencesScreen.add(glutenBox);
        preferencesScreen.add(dairyBox);


        frame.setVisible(true);

    }

    public void mealScreen(int mealNumber,String diningHall){
        DiningHall hall = new DiningHall(diningHall);
        mealScreen = new JPanel();
        mealScreen.setLayout(new FlowLayout());
        mealScreen.setSize(500,1300);
        itemPanelArr = new ArrayList<ItemPanel>();

        backButton = new JButton("<-");
        mealHeader = new JLabel(new String[]{"Breakfast","Lunch","Dinner"}[mealNumber-1]);
        EntreeHeader = new JLabel("Entrees");
        sidesHeader = new JLabel("Sides");
        desertsHeader = new JLabel("Desert");
        drinksLabel = new JLabel("Beverages");
        totalsPanel = new JPanel();
        slider = new JSlider(Adjustable.VERTICAL,0,400,0);
        searchBar = new JTextField();
        this.diningHall = new JLabel("1/19/23                   "+hall.name);//to change based in dining hall
        entreePanel = new JPanel(new BorderLayout());
        sidesPanel = new JPanel(new BorderLayout());
        drinksPanel = new JPanel(new BorderLayout());
        desertsPanel = new JPanel(new BorderLayout());
        itemListPanel = new JPanel();
        totalMealCalories = new JLabel("Total calories:    123");
        totalMealCarbs = new JLabel("Carbs:      32g");
        totalMealFat = new JLabel("Fat:     32g");
        totalMealProtein = new JLabel("Protein:      12g");
        moreNutrition = new JButton("More nutrition info...");




        totalsPanel.setBounds(0,600,500,200);
        backButton.setBounds(20,40,50,30);
        mealHeader.setBounds(150,18,300,100);
        slider.setBounds(480,200,20,200);
        searchBar.setBounds(360,100,120,30);
        this.diningHall.setBounds(50,100,300,30);
        EntreeHeader.setBounds(200,140,150,40);
        sidesHeader.setBounds(210,350,100,40);
        drinksLabel.setBounds(170, 560,200,40);
        desertsHeader.setBounds(190,770,200,40);
        entreePanel.setBounds(0,180,500,160);
        sidesPanel.setBounds(0,385,500,160);
        drinksPanel.setBounds(0,600,500,160);
        desertsPanel.setBounds(0,810,500,160);
        itemListPanel.setBounds(15,15,200,140);
        totalMealCalories.setBounds(250,25,200,40);
        totalMealCarbs.setBounds(250,60,150,20);
        totalMealFat.setBounds(250,85,150,20);
        totalMealProtein.setBounds(250,110,150,20);
        moreNutrition.setBounds(248,135,150,17);

        totalsPanel.setBackground(new Color(200,200,200));
        entreePanel.setBackground(new Color(204,203,143));
        sidesPanel.setBackground(Color.GRAY);
        drinksPanel.setBackground(Color.GRAY);
        desertsPanel.setBackground(new Color(204,203,143));

        mealHeader.setFont(new Font(Font.SERIF,Font.BOLD,50));
        this.diningHall.setFont(new Font(Font.SERIF,Font.BOLD,20));
        EntreeHeader.setFont(new Font(Font.SERIF,Font.BOLD,30));
        sidesHeader.setFont(new Font(Font.SERIF,Font.BOLD,33));
        drinksLabel.setFont(new Font(Font.SERIF,Font.BOLD,33));
        desertsHeader.setFont(new Font(Font.SERIF,Font.BOLD,33));
        totalMealCalories.setFont(new Font(Font.SERIF,Font.BOLD,22));
        totalMealCarbs.setFont(new Font(Font.SERIF,Font.BOLD,16));
        totalMealFat.setFont(new Font(Font.SERIF,Font.BOLD,16));
        totalMealProtein.setFont(new Font(Font.SERIF,Font.BOLD,16));

        backButton.addActionListener(actionListener);
        searchBar.addActionListener(actionListener);
        slider.addChangeListener(changeListener);
        moreNutrition.addActionListener(actionListener);






        mealScreen.add(backButton);
        mealScreen.add(mealHeader);
        content.add(totalsPanel);
        mealScreen.add(searchBar);
        mealScreen.add(this.diningHall);
        mealScreen.add(EntreeHeader);
        mealScreen.add(sidesHeader);
        mealScreen.add(drinksLabel);
        mealScreen.add(desertsHeader);
        mealScreen.add(entreePanel);
        mealScreen.add(sidesPanel);
        mealScreen.add(drinksPanel);
        mealScreen.add(desertsPanel);
        content.add(slider);
        totalsPanel.add(itemListPanel);
        totalsPanel.add(totalMealCalories);
        totalsPanel.add(totalMealCarbs);
        totalsPanel.add(totalMealFat);
        totalsPanel.add(totalMealProtein);
        totalsPanel.add(moreNutrition);

        ArrayList<ItemPanel> entrees = new ArrayList<>();
        ArrayList<ItemPanel> sides = new ArrayList<>();
        ArrayList<ItemPanel> desserts = new ArrayList<>();

        for(Item item: hall.entrees){
            entrees.add(new ItemPanel(item,hall.entrees.indexOf(item)*170+30,185));
            itemPanelArr.add(entrees.get(entrees.size()-1));
        }

        for(Item item: hall.sides){
            sides.add(new ItemPanel(item,hall.sides.indexOf(item)*170+30,390));
        }

        for(Item item: hall.desserts){
            desserts.add(new ItemPanel(item,hall.desserts.indexOf(item)*170+30,820));
        }

        for (ItemPanel entree : entrees) {
            content.add(entree);
        }
        for (ItemPanel side : sides) {
            content.add(side);
        }
        for (ItemPanel dessert : desserts) {
            content.add(dessert);
        }





       mealScreen.setVisible(true);
       content.repaint();


    }

    public void writeFiles(){
        try {
            PrintWriter userWriter = new PrintWriter(new FileWriter("storage/UserInfo.txt"));
            userWriter.println(user.name);
            userWriter.println(user.preferences.toString());
            userWriter.println(user.meals[0].toString());
            userWriter.flush();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void unitConverter(JTextField macro){
        int ratio = Integer.parseInt(macro.getText())*user.preferences.calorieGoal;
        if(units.getText().equals("g")){
            if (carbRatio.equals(macro)) {
                ratio = user.preferences.macros[0] * user.preferences.calorieGoal;
            }else if(fatRatio.equals(macro)){
                ratio = user.preferences.macros[1] * user.preferences.calorieGoal;
            }else{
                ratio = user.preferences.macros[2] * user.preferences.calorieGoal;
            }
        }
        preferencesScreen.remove(macro);
        if(!macro.equals(fatRatio)){
            macro.setText(String.valueOf(ratio/400));
        }else{
            macro.setText(String.valueOf(ratio/900));
        }
        preferencesScreen.add(macro);
        preferencesScreen.repaint();

    }

    public void convertByMacros(JTextField changed) {
        double ratio = 100 - user.preferences.macros[0];
        if(changed.equals(carbRatio)&& Integer.parseInt(changed.getText())*400/user.preferences.calorieGoal < 101){
            user.preferences.macros[0] = (Integer.parseInt(changed.getText())*400/user.preferences.calorieGoal);
            ratio = ((100-user.preferences.macros[0])/ratio);
            user.preferences.macros[1] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[1]))*ratio)));
            user.preferences.macros[2] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[2]))*ratio)));
        }else if(changed.equals(fatRatio)&& Integer.parseInt(changed.getText())*900/user.preferences.calorieGoal < 101){
            user.preferences.macros[1] = (Integer.parseInt(changed.getText())*900/user.preferences.calorieGoal);
            ratio = ((100-user.preferences.macros[1])/ratio);
            user.preferences.macros[0] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[0]))*ratio)));
            user.preferences.macros[2] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[2]))*ratio)));
        }else if(changed.equals(proteinRatio )&& Integer.parseInt(changed.getText())*400/user.preferences.calorieGoal < 101){
            user.preferences.macros[2] = (Integer.parseInt(changed.getText())*400/user.preferences.calorieGoal);
            ratio = ((100-user.preferences.macros[2])/ratio);
            user.preferences.macros[0] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[0]))*ratio)));
            user.preferences.macros[1] = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(String.valueOf(user.preferences.macros[1]))*ratio)));
        }else{
            JOptionPane.showMessageDialog(preferencesScreen,"Error- Macro amount exceeds calorie goal.","BoilerMacros",JOptionPane.ERROR_MESSAGE);
            return;
        }
        unitConverter(carbRatio);
        unitConverter(fatRatio);
        unitConverter(proteinRatio);
    }

    @Override
    protected  void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics.create();
        graphics2D.setColor(Color.PINK);
        graphics2D.fillRect(200,200,100,50);
    }



}