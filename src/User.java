public class User {

   String name;

    Preferences preferences;

    Meal[] meals = new Meal[3];

    private int calories;

    private int carbs;
    private double fat;
    private int protein;

    private double saturatedFat;

    private int cholesterol;

    private int sodium;

    private int sugar;

    private  int fiber;


    private int calcium;

    private int iron;

    public User(String name, String preferences, String meals){
        this.name = name;

        this.preferences = new Preferences(preferences);

        int meal = 0;
        while(meals.contains("]")){
        this.meals[meal] = new Meal(meals.substring(0,meals.indexOf(']')));
            updateStats();
        try {
            meals = meals.substring(meals.indexOf(']') + 1);
        }catch (IndexOutOfBoundsException e){
            break;
        }
        meal++;
        }

    }


    public Preferences getPreferences(){
        return  preferences;
    }

    public void updateStats(){
        for(Meal meal: meals){
            if(meal != null){
                calories = meal.getCalories();
                carbs = meal.getCarbs();
                fat = meal.getFat();
                protein = meal.getProtein();
                fiber = meal.getFiber();
                calcium = meal.getCalcium();
                iron = meal.getIron();
                sugar = meal.getSugar();
                sodium = meal.getSodium();
                cholesterol = meal.getCholesterol();

            }
        }

    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public int getProtein() {
        return protein;
    }

    public int getFiber() {
        return fiber;
    }

    public int getCalcium() {
        return calcium;
    }

    public int getIron() {
        return iron;
    }

    public int getSugar(){
        return sugar;
    }

    public int getSodium() {
        return sodium;
    }

    public int getCholesterol() {
        return cholesterol;
    }
}

