import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JComponent implements Runnable{

    //GUI stuff
    JFrame frame;
    Container content;

    static final String date = String.valueOf(LocalDateTime.now().toLocalDate());

    public static final String[] entreeKeywords = new String[]{"eggs","chicken","beef","pork","pasta","pizza","sausage","burger","patty","fish","shrimp","crab","turkey"};// keywords for sorting foods into "entree" catagory,basically high protein foods
    public static final String[] drinkKeywords = new String[]{"juice","milk"};
    public static final String[] diningHalls = new String[]{"Wiley","Earhart","Ford","Windsor","Hillenbrand"};// the dining halls
    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource().equals(preferencesButton)){//the ... button in the corner of main screen

                content.removeAll();
                frame.repaint();
                preferencesScreen();
                content.add(preferencesScreen,BorderLayout.CENTER);// adds preferences screen

            }

            else if(e.getSource().equals(settingsButton)){
                writeFiles();// idk what i want this button to do
            }

            else if(e.getSource().equals(meal1)){// buttons for each meal on the main screen, shows option pane asking what dining hall and then displays meal screen
                content.removeAll();
                content.repaint();
                combineItems(0);
                if(user.meals[0].diningHall.equals("")){
                    mealScreen(1, (String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[0]));// the dining hall popup
                }else{
                    mealScreen(1,user.meals[0].diningHall);
                }
                content.add(mealScreen);
            }else if(e.getSource().equals(meal2)){// buttons for each meal on the main screen, shows option pane asking what dining hall and then displays meal screen
                content.removeAll();
                content.repaint();
                combineItems(1);
                if(user.meals[1].diningHall.equals("")){
                    mealScreen(2, (String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[1]));// the dining hall popup
                }else{
                    mealScreen(2,user.meals[1].diningHall);
                }
                content.add(mealScreen);
            }else if(e.getSource().equals(meal3)){// buttons for each meal on the main screen, shows option pane asking what dining hall and then displays meal screen
                content.removeAll();
                content.repaint();
                combineItems(2);
                if(user.meals[2].diningHall.equals("")){
                    mealScreen(3, (String) JOptionPane.showInputDialog(mainScreen,"Select a dining hall:","BoilerMacros",JOptionPane.PLAIN_MESSAGE,null,diningHalls,diningHalls[2]));// the dining hall popup
                }else{
                    mealScreen(3,user.meals[2].diningHall);
                }
                content.add(mealScreen);
            }

            else if(e.getSource().equals(backButton)){//back to the main menu
                if(backButton.getParent().equals(preferencesScreen)) {

                    user.preferences.portions = (double) slider.getValue() / 100;// updating allergy checkboxes
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
                    if (user.preferences.macros[0] + user.preferences.macros[1] + user.preferences.macros[2] != 100) {// checks to see if macros add up to 100%
                        JOptionPane.showMessageDialog(preferencesScreen, "Macro ratio must add up to 100", "BoilerMacros", JOptionPane.ERROR_MESSAGE);

                        //TODO: ooga booga
                        return;
                    }
                }
                writeFiles();// updates preference and meal info
                content.removeAll();
                frame.repaint();
                mainScreen();
                content.add(mainScreen);// add main menu
            }

            else if(e.getSource().equals(addFiberGoal)){//removes button and adds text field
                preferencesScreen.repaint();
                fiberGoalField.setText("");
                fiberGoalField.setBackground(Color.BLACK);//^^
                preferencesScreen.remove(addFiberGoal);
                preferencesScreen.add(fiberGoal);
                preferencesScreen.add(fiberGoalField);

        }

            else if(e.getSource().equals(addCalciumGoal)){//^^
            preferencesScreen.repaint();
            calciumGoalField.setText("");
            calciumGoal.setBackground(Color.WHITE);
            preferencesScreen.remove(addCalciumGoal);
            preferencesScreen.add(calciumGoal);
            preferencesScreen.add(calciumGoalField);

        }

            else if(e.getSource().equals(addIronGoal)){//^^
                preferencesScreen.repaint();
                ironGoalField.setText("");
                ironGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addIronGoal);
                preferencesScreen.add(ironGoal);
                preferencesScreen.add(ironGoalField);

            }

            else if(e.getSource().equals(addSugarGoal)){//^^
                preferencesScreen.repaint();
                sugarGoalField.setText("");
                sugarGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addSugarGoal);
                preferencesScreen.add(sugarGoal);
                preferencesScreen.add(sugarGoalField);

            }

            else if(e.getSource().equals(addSodiumGoal)){//^^
                preferencesScreen.repaint();
                sodiumGoalField.setText("");
                sodiumGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addSodiumGoal);
                preferencesScreen.add(sodiumGoal);
                preferencesScreen.add(sodiumGoalField);

            }
            else if(e.getSource().equals(addCholesterolGoal)) {//^^
                preferencesScreen.repaint();
                cholesterolGoalField.setText("");
                cholesterolGoalField.setBackground(Color.WHITE);
                preferencesScreen.remove(addCholesterolGoal);
                preferencesScreen.add(cholesterolGoal);
                preferencesScreen.add(cholesterolGoalField);

            }if (mealScreen != null) {
                if (e.getSource() instanceof JButton && mealScreen.isVisible()) {//weird conditions to specify the nutrition button
                        if (((JButton) e.getSource()).getText().equals("More nutrition info")) {
                            if (((JButton) e.getSource()).getParent() instanceof ItemPanel) {
                                nutritionWindow = new NutritionWindow(((ItemPanel) ((JButton) e.getSource()).getParent()).item, actionListener,itemListener,1);// adds nutrition window
                                nutritionWindow.multiplierBox.setSelectedItem(1.0);

                            }
                        }
                    }

            }
            if(e.getSource() instanceof  JButton && mealScreen != null) {
                if (((JButton) e.getSource()).getText().equals("OK")) {
                    if (mealHeader.getText().equals("Breakfast")) {
                        user.meals[0].items.add(window.item.name);
                        user.meals[0].size.add(window.servingUnits[window.quantityBox.getSelectedIndex()]);
                        combineItems(0);
                        writeFiles();
                        readFiles();
                        content.repaint();
                        window.dispose();
                        content.removeAll();
                        mealScreen(1,diningHall.getText().substring(26));
                        content.add(mealScreen);
                    } else if (mealHeader.getText().equals("Lunch")) {
                        user.meals[1].items.add(window.item.name);
                        user.meals[1].size.add(window.servingUnits[window.quantityBox.getSelectedIndex()]);
                        combineItems(1);
                        writeFiles();
                        readFiles();
                        content.repaint();
                        window.dispose();
                        content.removeAll();
                        mealScreen(2,diningHall.getText().substring(26));
                        content.add(mealScreen);
                    } else if (mealHeader.getText().equals("Dinner")) {
                        user.meals[2].items.add(window.item.name);
                        user.meals[2].size.add(window.servingUnits[window.quantityBox.getSelectedIndex()]);
                        combineItems(2);
                        writeFiles();
                        readFiles();
                        content.repaint();
                        window.dispose();
                        content.removeAll();
                        mealScreen(3,diningHall.getText().substring(26));
                        content.add(mealScreen);
                    }

                } else if (((JButton) e.getSource()).getText().equals("Cancel")) {
                    // cancel button
                    window.dispose();
                }

            }if(e.getSource().equals(deleteButton)){
                content.repaint();
                if(deleteMode){
                    deleteMode = false;
                for(int i = 0; i < user.meals[Arrays.asList(new String[]{"Breakfast","Lunch","Dinner"}).indexOf(mealHeader.getText())].items.size(); i++){
                    innerPanel.remove(itemTotalSizes.get(i));
                    deleteButtons.add(new JButton("x"));
                    deleteButtons.get(i).setBounds(150,i*20+25,18,18);
                    deleteButtons.get(i).addActionListener(actionListener);
                    innerPanel.add(deleteButtons.get(i));

                }
            }else{
                    deleteMode = true;
                    for(int i = 0; i < user.meals[Arrays.asList(new String[]{"Breakfast","Lunch","Dinner"}).indexOf(mealHeader.getText())].items.size(); i++){
                        innerPanel.remove(deleteButtons.get(i));
                        innerPanel.add(itemTotalSizes.get(i));
                    }
                }

            }if(deleteButtons.contains(e.getSource())){
                content.repaint();
                innerPanel.remove(((JButton)e.getSource()));
                innerPanel.remove(innerPanel.getComponent(deleteButtons.indexOf(e.getSource())+1));
                itemTotalSizes.remove(deleteButtons.indexOf(e.getSource()));
                user.meals[Arrays.asList(new String[]{"Breakfast","Lunch","Dinner"}).indexOf(mealHeader.getText())].items.remove(deleteButtons.indexOf(e.getSource()));
                user.meals[Arrays.asList(new String[]{"Breakfast","Lunch","Dinner"}).indexOf(mealHeader.getText())].size.remove(deleteButtons.indexOf(e.getSource()));
                deleteButtons.remove(deleteButtons.indexOf(e.getSource()));
                writeFiles();
                readFiles();
                content.repaint();

            }

        }

    };

    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource().equals(unitSwitcher)){
                preferencesScreen.remove(units);
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
            }else if(((JComboBox)e.getSource()).getParent().getLayout() instanceof  GridLayout && e.getStateChange() ==2){
                content.repaint();
                nutritionWindow.dispose();
                double flint = nutritionWindow.multiplierBox.getSelectedIndex();
                nutritionWindow = new NutritionWindow(nutritionWindow.item,actionListener,itemListener,nutritionWindow.multiplier*nutritionWindow.servingUnits[nutritionWindow.multiplierBox.getSelectedIndex()]);
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
            if(e.getSource() instanceof JPanel) {
                window = new SelectionWindow(((ItemPanel) e.getSource()).item, ((ItemPanel) e.getSource()).portionsize, actionListener, itemListener);
            }





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
                    switch (((ItemPanel) component).item.type) {
                        case "entree" -> {
                            component.setLocation(((ItemPanel) component).x - entreesSlider.getValue(), ((ItemPanel) component).y - slider.getValue());
                            continue;
                        }
                        case "side" -> {
                            component.setLocation(((ItemPanel) component).x - sidesSlider.getValue(), ((ItemPanel) component).y - slider.getValue());
                            continue;
                        }
                        case "drink" -> {
                            component.setLocation(((ItemPanel) component).x - drinkSlider.getValue(), ((ItemPanel) component).y - slider.getValue());
                            continue;
                        }
                        case "dessert" ->
                                component.setLocation(((ItemPanel) component).x - dessertSlider.getValue(), ((ItemPanel) component).y - slider.getValue());
                    }
                }
                }entreesSlider.setLocation(100,338-slider.getValue());
                sidesSlider.setLocation(100,550-slider.getValue());
                drinkSlider.setLocation(100,775-slider.getValue());
                dessertSlider.setLocation(100,1000-slider.getValue());
                mealScreen.setLocation(0,-slider.getValue());
                content.repaint();
            }else if(e.getSource().equals(entreesSlider)){
                for(Component component : content.getComponents()){
                    if(component instanceof ItemPanel) {
                        if (((ItemPanel) component).item.type.equals("entree")) {
                            component.setLocation(((ItemPanel) component).x - entreesSlider.getValue(), ((ItemPanel) component).y-slider.getValue());
                        }
                    }

                }
            }else if(e.getSource().equals(sidesSlider)){
                for(Component component : content.getComponents()){
                    if(component instanceof ItemPanel) {
                        if (((ItemPanel) component).item.type.equals("side")) {
                            component.setLocation(((ItemPanel) component).x - sidesSlider.getValue(), ((ItemPanel) component).y-slider.getValue());
                        }
                    }

                }
            }else if(e.getSource().equals(dessertSlider)){
                for(Component component : content.getComponents()){
                    if(component instanceof ItemPanel) {
                        if (((ItemPanel) component).item.type.equals("dessert")) {
                            component.setLocation(((ItemPanel) component).x - sidesSlider.getValue(), ((ItemPanel) component).y-slider.getValue());
                        }
                    }

                }
            }else if(e.getSource().equals(totalsScroller)){
                            innerPanel.setLocation(0, 10-totalsScroller.getValue());

            }


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
    JPanel breakfastCaloriesPanel;
    JPanel lunchCaloriesPanel;
    JPanel dinnerCaloriesPanel;

    JPanel totalCalories;
    JPanel carbsPanel;
    JPanel fatPanel;
    JPanel proteinPanel;





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
    ArrayList<JLabel> itemTotals;
    ArrayList<JLabel> itemTotalSizes;
    JPanel itemListPanel;
    JButton moreNutrition;

    ArrayList<ItemPanel> itemPanelArr;
    ArrayList<JLabel> itemLabels;
    NutritionWindow nutritionWindow;

    SelectionWindow window;
    JButton deleteButton;
    ArrayList<JButton> deleteButtons;
    JSlider entreesSlider;
    JSlider sidesSlider;
    JSlider drinkSlider;
    JSlider dessertSlider;
    JPanel innerPanel;
    JSlider totalsScroller;
    JLabel totalsLabel;
    boolean deleteMode = true;


    public static ArrayList<Item> items = new ArrayList<>();
    static User user;

    public static void main(String[] args) {

        try {
            System.out.print(date);
            //adding food to item list
            BufferedReader listedReader = new BufferedReader(new FileReader("storage/fooditems/listedfoods.txt"));
            while (true) {
                String line = listedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] itemArr = line.split("\\,");
                items.add(new Item(itemArr[0], itemArr[1], Integer.parseInt(itemArr[2]),
                        Double.parseDouble(itemArr[3]), Double.parseDouble(itemArr[4]), Integer.parseInt(itemArr[5]),
                        Integer.parseInt(itemArr[6]), Integer.parseInt(itemArr[7]),
                        Integer.parseInt(itemArr[8]), Integer.parseInt(itemArr[9]), Integer.parseInt(itemArr[10]),
                        Integer.parseInt(itemArr[11]), Integer.parseInt(itemArr[12]), itemArr[13], itemArr[14], itemArr[15]));
            }
            listedReader.close();

            BufferedReader unlistedReader = new BufferedReader(new FileReader("storage/fooditems/unlistedfoods.txt"));
            while (true) {
                String line = unlistedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] itemArr = line.split("\\,");
                items.add(new Item(itemArr[0], itemArr[1], Integer.parseInt(itemArr[2]),
                        Double.parseDouble(itemArr[3]), Double.parseDouble(itemArr[4]), Integer.parseInt(itemArr[5]),
                        Integer.parseInt(itemArr[6]), Integer.parseInt(itemArr[7]),
                        Integer.parseInt(itemArr[8]), Integer.parseInt(itemArr[9]), Integer.parseInt(itemArr[10]),
                        Integer.parseInt(itemArr[11]), Integer.parseInt(itemArr[12]), itemArr[13], itemArr[14], itemArr[15]));
            }
            unlistedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        readFiles();
            SwingUtilities.invokeLater(new Main());

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
        readFiles();
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
        meal1 = new JButton("Breakfast");
        meal2 = new JButton("Lunch");
        meal3 = new JButton("Dinner");
        preferencesButton = new JButton("...");
        settingsButton = new JButton("Settings");


        //text fields
        calories = new JLabel(user.getCalories()+" kcals");
        carbs = new JLabel(user.getCarbs()+"g carbs");
        fat = new JLabel(user.getFat()+"g fat");
        protein = new JLabel(user.getProtein()+"g protein");
        fiber = new JLabel("Fiber: "+user.getFiber()+"g");
        calcium = new JLabel("Calcium: "+user.getCalcium()+"mg");
        iron = new JLabel("Iron: "+user.getIron()+"mg");
        sugar = new JLabel("Sugar: "+user.getSugar()+"g");
        sodium = new JLabel("Sodium: "+user.getSodium()+"mg");
        cholesterol = new JLabel("Cholesterol: "+user.getCholesterol()+"mg");
        title = new JLabel(greeting + "."); //rename later
        breakfastCaloriesPanel = new JPanel();
        lunchCaloriesPanel = new JPanel();
        dinnerCaloriesPanel = new JPanel();
        totalCalories = new JPanel();
        carbsPanel = new JPanel();
        fatPanel = new JPanel();
        proteinPanel = new JPanel();



        meal1.setLocation(60,450);
        meal2.setLocation(60,550);
        meal3.setLocation(60,650);
        preferencesButton.setBounds(400,20,70,40);
        settingsButton.setBounds(50,20,70,40);
        calories.setBounds(230,70,200,100);
        carbs.setBounds(110,160,110,110);
        fat.setBounds(220,160,170,110);
        protein.setBounds(330,160,110,110);
        fiber.setBounds(70,290,170,50);
        calcium.setBounds(70,330,200,50);
        iron.setBounds(70,370,200,50);
        sugar.setBounds(280,290,170,50);
        sodium.setBounds(280,330,170,50);
        cholesterol.setBounds(280,370,220,50);
        try {
            breakfastCaloriesPanel.setBounds(70, 145, 350 * user.meals[0].getCalories() / user.preferences.calorieGoal, 30);
            lunchCaloriesPanel.setBounds(70 + breakfastCaloriesPanel.getWidth(), 145, 350 * user.meals[1].getCalories() / user.preferences.calorieGoal, 30);
            dinnerCaloriesPanel.setBounds(70 + lunchCaloriesPanel.getWidth() + breakfastCaloriesPanel.getWidth(), 145, 350 * user.meals[2].getCalories() / user.preferences.calorieGoal, 30);
            carbsPanel.setBounds(70,240, (int) (350*user.getProtein()/(user.getCarbs()+user.getFat()+user.getProtein())),30);
            fatPanel.setBounds(70+(int) (350*user.getCarbs()/(user.getCarbs()+user.getFat()+user.getProtein())),240, (int) (350*user.getFat()/(user.getCarbs()+user.getFat()+user.getProtein())),30);
            proteinPanel.setBounds(70+(int) (350*user.getFat()/(user.getCarbs()+user.getFat()+user.getProtein()))+(int) (350*user.getCarbs()/(user.getCarbs()+user.getFat()+user.getProtein())),240, (int) (350*user.getProtein()/(user.getCarbs()+user.getFat()+user.getProtein())),30);
        }catch(NullPointerException e){

        }
        totalCalories.setBounds(70,145,350,30);

        preferencesButton.setFont(new Font("Serif", Font.BOLD, 15));
        calories.setFont(new Font("Serif", Font.BOLD, 20));
        carbs.setFont(new Font("Serif", Font.BOLD, 20));
        fat.setFont(new Font("Serif", Font.BOLD, 20));
        protein.setFont(new Font("Serif", Font.BOLD, 20));
        breakfastCaloriesPanel.setBackground(Color.ORANGE);
        lunchCaloriesPanel.setBackground(Color.RED);
        dinnerCaloriesPanel.setBackground(Color.GREEN);
        totalCalories.setBackground(Color.DARK_GRAY);
        carbsPanel.setBackground(Color.cyan);
        fatPanel.setBackground(Color.yellow);
        proteinPanel.setBackground(Color.BLUE);


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
        meal2.setForeground(Color.BLACK);
        meal3.addActionListener(actionListener);
        meal3.setBackground(Color.DARK_GRAY);
        meal3.setForeground(Color.BLACK);
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
        mainScreen.add(breakfastCaloriesPanel);
        mainScreen.add(lunchCaloriesPanel);
        mainScreen.add(dinnerCaloriesPanel);
        mainScreen.add(totalCalories);
        mainScreen.add(carbsPanel);
        mainScreen.add(fatPanel);
        mainScreen.add(proteinPanel);

        //greeting
        title.setFont(new Font("Serif", Font.BOLD, 34));//good day sir
        title.setSize(550, 175);
        title.setLocation(50, 0);


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
        String meal = new String[]{"b","l","d"}[mealNumber-1];
        DiningHall hall = new DiningHall(diningHall);
        user.meals[mealNumber-1].diningHall=hall.name;
        mealScreen = new JPanel();
        mealScreen.setLayout(new FlowLayout());
        mealScreen.setSize(500,1400);
        itemLabels = new ArrayList<>();
        itemPanelArr = new ArrayList<>();
        backButton = new JButton("<-");
        mealHeader = new JLabel(new String[]{"Breakfast","Lunch","Dinner"}[mealNumber-1]);
        EntreeHeader = new JLabel("Entrees");
        sidesHeader = new JLabel("Sides");
        desertsHeader = new JLabel("Dessert");
        drinksLabel = new JLabel("Beverages");
        totalsPanel = new JPanel();
        slider = new JSlider(Adjustable.VERTICAL,0,440,0);
        searchBar = new JTextField();
        this.diningHall = new JLabel(date.substring(5,7)+"/"+date.substring(8,10)+"/"+date.substring(2,4)+"                  "+hall.name);//to change based in dining hall
        entreePanel = new JPanel(new BorderLayout());
        sidesPanel = new JPanel(new BorderLayout());
        drinksPanel = new JPanel(new BorderLayout());
        desertsPanel = new JPanel(new BorderLayout());
        itemListPanel = new JPanel();
        totalMealCalories = new JLabel("Total calories:   "+user.meals[mealNumber-1].getCalories());
        totalMealCarbs = new JLabel("Carbs:     "+user.meals[mealNumber-1].getCarbs());
        totalMealFat = new JLabel("Fat:    "+user.meals[mealNumber-1].getFat());
        totalMealProtein = new JLabel("Protein:     "+user.meals[mealNumber-1].getProtein());
        moreNutrition = new JButton("More nutrition info...");
        itemTotals = new ArrayList<>();
        itemTotalSizes = new ArrayList<>();
        deleteButton = new JButton("-");
        deleteButtons = new ArrayList<>();
       // entreesSlider = new JSlider();
        entreesSlider = new JSlider(JSlider.HORIZONTAL, 0, hall.entrees.size() * 80, 0);
        sidesSlider = new JSlider(JSlider.HORIZONTAL,0,hall.sides.size()*80,0);
        drinkSlider = new JSlider(JSlider.HORIZONTAL,0,hall.drinks.size()*80,0);
        dessertSlider = new JSlider(JSlider.HORIZONTAL,0,hall.desserts.size()*80,0);
        innerPanel = new JPanel();
        totalsScroller = new JSlider(JSlider.VERTICAL,0,100,0);
        totalsLabel = new JLabel("Item            # of servings");





        totalsPanel.setBounds(0,600,500,200);
        backButton.setBounds(20,40,50,30);
        mealHeader.setBounds(150,18,300,100);
        slider.setBounds(480,200,20,200);
        searchBar.setBounds(360,100,120,30);
        this.diningHall.setBounds(50,100,300,30);
        EntreeHeader.setBounds(200,140,150,40);
        sidesHeader.setBounds(210,360,100,40);
        drinksLabel.setBounds(170, 580,200,40);
        desertsHeader.setBounds(190,800,200,40);
        entreePanel.setBounds(0,180,500,180);
        sidesPanel.setBounds(0,395,500,180);
        drinksPanel.setBounds(0,620,500,180);
        desertsPanel.setBounds(0,840,500,180);
        totalsPanel.setBounds(0,600,500,180);
        itemListPanel.setBounds(10,10,200,153);
        totalMealCalories.setBounds(270,30,200,40);
        totalMealCarbs.setBounds(270,70,150,20);
        totalMealFat.setBounds(270,95,150,20);
        totalMealProtein.setBounds(270,120,150,20);
        moreNutrition.setBounds(268,142,150,17);
        deleteButton.setBounds(212,10,20,20);
        entreesSlider.setBounds(100,338,300,20);
        sidesSlider.setBounds(100,550,300,20);
        drinkSlider.setBounds(100,775,300,20);
        dessertSlider.setBounds(100,950,300,20);
        innerPanel.setSize(200,400);
        totalsScroller.setBounds(213,30,20,120);
        totalsLabel.setBounds(7,0,200,20);

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
        entreesSlider.addChangeListener(changeListener);
        sidesSlider.addChangeListener(changeListener);
        drinkSlider.addChangeListener(changeListener);
        dessertSlider.addChangeListener(changeListener);
        moreNutrition.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        totalsScroller.addChangeListener(changeListener);






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
        content.add(totalsPanel);
        content.add(slider);
        totalsPanel.add(itemListPanel);
        totalsPanel.add(totalMealCalories);
        totalsPanel.add(totalMealCarbs);
        totalsPanel.add(totalMealFat);
        totalsPanel.add(totalMealProtein);
        totalsPanel.add(moreNutrition);
        totalsPanel.add(deleteButton);
        itemListPanel.add(innerPanel);
        content.add(entreesSlider);
        content.add(sidesSlider);
        content.add(drinkSlider);
        content.add(dessertSlider);
        totalsPanel.add(totalsScroller);
        innerPanel.add(totalsLabel);

        ArrayList<ItemPanel> entrees = new ArrayList<>();
        ArrayList<ItemPanel> sides = new ArrayList<>();
        ArrayList<ItemPanel> drinks = new ArrayList<>();
        ArrayList<ItemPanel> desserts = new ArrayList<>();

        int counter = 0;
        for(Item item: hall.entrees){
            String mealStr = item.location.split("-")[2];
            if(mealStr.contains(meal)) {
                entrees.add(new ItemPanel(item, (hall.entrees.indexOf(item)-counter) * 170 + 10, 190, mouseListener, actionListener));
                itemPanelArr.add(entrees.get(entrees.size() - 1));
            }else{
                counter++;
            }
        }

        for(Item item: hall.sides){
            String mealStr = item.location.split("-")[2];
            if(mealStr.contains(meal)) {
                sides.add(new ItemPanel(item, hall.sides.indexOf(item) * 170 + 10, 405, mouseListener, actionListener));
            }
        }

        for(Item item: hall.drinks){
            String mealStr = item.location.split("-")[2];
            if(mealStr.contains(meal)) {
                drinks.add(new ItemPanel(item, hall.drinks.indexOf(item) * 170 + 10, 630, mouseListener, actionListener));
            }
        }

        for(Item item: hall.desserts){
            String mealStr = item.location.split("-")[2];
            if(mealStr.contains(meal)) {
                desserts.add(new ItemPanel(item, hall.desserts.indexOf(item) * 170 + 10, 855, mouseListener, actionListener));
            }
        }

        for (ItemPanel entree : entrees) {
            content.add(entree);
        }
        for (ItemPanel side : sides) {
            content.add(side);
        }
        for (ItemPanel drink : drinks) {
            content.add(drink);
        }
        for (ItemPanel dessert : desserts) {
            content.add(dessert);
        }

        for(int i = 0; i < user.meals[mealNumber-1].items.size(); i++){
            if(user.meals[mealNumber-1].items.size() < 1){
                break;
            }
            itemLabels.add(new JLabel(user.meals[mealNumber-1].items.get(i)));
            itemTotalSizes.add(new JLabel(String.valueOf(user.meals[mealNumber-1].size.get(i))));
            itemLabels.get(itemLabels.size()-1).setBounds(5,i*20+25,190,20);
            itemTotalSizes.get(itemTotalSizes.size()-1).setBounds(120,i*20+25,190,20);
            innerPanel.add(itemLabels.get(i));
            innerPanel.add(itemTotalSizes.get(i));
            if(i+1 == user.meals[mealNumber-1].items.size()){
                break;
            }

        }



       mealScreen.setVisible(true);
       content.repaint();
    }

    public void writeFiles(){
        try {
            PrintWriter userWriter = new PrintWriter(new FileWriter("storage/UserInfo.txt"));
            userWriter.println(user.name);
            userWriter.println(user.preferences.toString());
            for(int i = 0; i < 3; i++){
                try {
                    userWriter.print(user.meals[i].toString());
                }catch (Exception e){
                    try{
                        userWriter.print("[]");
                    }catch (Exception f){
                        System.out.println("fuck me");
                    }
                }
            }
            userWriter.println();
            userWriter.flush();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void readFiles(){
        try {
            //read user info
            BufferedReader userReader = new BufferedReader(new FileReader("storage/UserInfo.txt"));
            String name = userReader.readLine();
            String preferences = userReader.readLine();
            String meals = userReader.readLine();
            userReader.close();

            user = new User(name, preferences, meals);

        } catch (Exception e) {
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

    public void combineItems(int meal){

        for(int i = 0; i < user.meals[meal].items.size(); i++){
            String name = user.meals[meal].items.get(i);
            double size = user.meals[meal].size.get(i);
            for(int j = i+1; j < user.meals[meal].items.size(); j++){
                if(user.meals[meal].items.get(j).equals(name)){
                    user.meals[meal].size.set(i,size+user.meals[meal].size.get(j));
                    user.meals[meal].items.remove(j);
                    user.meals[meal].size.remove(j);
                }


            }

        }
    }

    @Override
    protected  void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics.create();
        graphics2D.setColor(Color.PINK);
        graphics2D.fillRect(200,200,100,50);
    }



}
