import java.time.LocalTime;
import java.util.ArrayList;

public class Meal {
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<Double> size = new ArrayList<>();

    int calories;

    int[] macros = new int[3];

    LocalTime timeStarted;

    LocalTime timeEnded;

    public Meal(String itemString){
        String[]itemArr = itemString.substring(1,itemString.length()).split("-");

        for(int i = 0; i < itemArr.length; i = i+2){
            if (itemArr[i].equals("")){
                break;
            }
            items.add(itemArr[i]);
            size.add(Double.parseDouble(itemArr[i+1]));
        }
    }

    public int getCalories(){
        int totalCalories = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalCalories += size.get(i)*Main.items.get(j).calories;
                    break;
                }
            }
        }
        return totalCalories;

    }

    public int getCarbs(){
        int totalCarbs = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalCarbs += size.get(i)*Main.items.get(j).carbs;
                    break;
                }
            }
        }
        return totalCarbs;

    }

    public double getFat(){
        double totalFat = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalFat += size.get(i)*Main.items.get(j).fat;
                    break;
                }
            }
        }
        return totalFat;

    }

    public int getProtein(){
        int totalProtein = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalProtein += size.get(i)*Main.items.get(j).protein;
                    break;
                }
            }
        }
        return totalProtein;

    }

    public int getFiber(){
        int totalFiber = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalFiber += size.get(i)*Main.items.get(j).fiber;
                    break;
                }
            }
        }
        return totalFiber;

    }

    public int getCalcium(){
        int totalCalcium = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalCalcium += size.get(i)*Main.items.get(j).calcium;
                    break;
                }
            }
        }
        return totalCalcium;

    }

    public int getIron(){
        int totalIron = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalIron += size.get(i)*Main.items.get(j).iron;
                    break;
                }
            }
        }
        return totalIron;

    }

    public int getSugar(){
        int totalSugar = 0;
        for(int i = 0; i < items.size(); i ++){
            for(int j = 0; j < Main.items.size(); j++){
                if(Main.items.get(j).name.equals(items.get(i))){
                    totalSugar += size.get(i)*Main.items.get(j).sugar;
                    break;
                }
            }
        }
        return totalSugar;

    }

    public int getSodium() {
        int totalSodium = 0;
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < Main.items.size(); j++) {
                if (Main.items.get(j).name.equals(items.get(i))) {
                    totalSodium += size.get(i) * Main.items.get(j).sodium;
                    break;
                }
            }
        }
        return totalSodium;
    }

    public int getCholesterol() {
        int totalCholesterol = 0;
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < Main.items.size(); j++) {
                if (Main.items.get(j).name.equals(items.get(i))) {
                    totalCholesterol += size.get(i) * Main.items.get(j).sodium;
                    break;
                }
            }
        }
        return totalCholesterol;
    }


    public String toString(){
        String mealArr = "[";
        for(int i = 0; i < items.size(); i++){
            mealArr += items.get(i)+"-"+size.get(i)+"-";
        }
        return(mealArr.substring(0,mealArr.length()-1)+"]");
    }


}
