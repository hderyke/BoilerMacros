import java.util.ArrayList;

public class Preferences {

    ArrayList<String> allergies = new ArrayList<>();

    double portions;

  //  Meal[] constants = new Meal[3];

    int calorieGoal;

    int[] macros = new int[3];

    int fiberGoal;

    int calciumGoal;

    int ironGoal;

    int sugarGoal;

    int sodiumGoal;

    int cholesterolGoal;

    boolean isVegetarian;

    boolean isVegan;

    boolean isGlutenFree;

    boolean isDairyFree;

    public Preferences(String preferences){
        String[] preferencesArr = preferences.split("\\,");
        try {
            String[] arr = preferencesArr[0].split("-");
            for (String term : arr) {
                allergies.add(term);
            }
        }catch(Exception e){
            allergies = new ArrayList<>();
        }
        portions = Double.parseDouble(preferencesArr[1]);
        calorieGoal = Integer.parseInt(preferencesArr[2]);
        macros = new int[]{Integer.parseInt(preferencesArr[3]), Integer.parseInt(preferencesArr[4]), Integer.parseInt(preferencesArr[5])};
        isVegetarian = Boolean.parseBoolean(preferencesArr[12]);
        isVegan = Boolean.parseBoolean(preferencesArr[13]);
        isGlutenFree = Boolean.parseBoolean(preferencesArr[14]);
        isDairyFree = Boolean.parseBoolean(preferencesArr[15]);

        for(int i = 6; i < 12; i++){
            int goal;
            if(!preferencesArr[i].equals("null")){
                goal = Integer.parseInt(preferencesArr[i]);
            }else{
                goal = -1;
            }
            switch (i) {
                case 6 -> {
                    fiberGoal = goal;
                    continue;
                }
                case 7 -> {
                    calciumGoal = goal;
                    continue;
                }
                case 8 -> {
                    ironGoal = goal;
                    continue;
                }
                case 9 -> {
                    sugarGoal = goal;
                    continue;
                }
                case 10 -> {
                    sodiumGoal = goal;
                    continue;
                }
                case 11 -> cholesterolGoal = goal;
            }


        }

    }
@Override
    public String toString(){
    String allergiesArr = "";
        try {
            for (String item : allergies) {
                allergiesArr += (item + "-");
            }
            allergiesArr = allergiesArr.substring(0, allergiesArr.length() - 1);
        }catch(Exception e){
            e.printStackTrace();
        }

        return(allergiesArr+","+portions+","+calorieGoal+","+macros[0]+","+macros[1]+","+macros[2]+","+fiberGoal+","+calciumGoal+","+ironGoal+","+sugarGoal+","+sodiumGoal+","+cholesterolGoal+","+isVegetarian+","+isVegan+","+isGlutenFree+","+isDairyFree);
    }






}
