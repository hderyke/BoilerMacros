public class Item {

     public String name;

     public String type;

     public String serving;

     public int calories;

     public double fat;

     public double saturatedFat;

     public int cholesterol;

     public int sodium;

     public int carbs;

     public int sugar;

     public int fiber;

     public int protein;

     public int calcium;

     public double iron;

     public String location;


     public boolean isVegetarian = false;

     public boolean isVegan = false ;

     public boolean hasDairy = false ;

     public boolean hasGluten = false;


     public String[] ingredients;

     public int[] macroArr = new int[3];

     Item(String name,String serving, int calories, double fat, double saturatedFat, int cholesterol, int sodium,
          int carbs, int sugar, int fiber, int protein, int calcium, int iron, String booleans,String ingredients,String location){
         this.name = name;
         this.serving = serving;
         this.calories = calories;
         this.fat = fat;
          this.saturatedFat = saturatedFat;
          this.cholesterol = cholesterol;
          this.sodium = sodium;
          this.carbs = carbs;
          this.sugar = sugar;
          this.fiber = fiber;
          this.protein = protein;
          this.calcium = calcium;
          this.iron = iron;
          type = "side";;

          this.calcium = this.calcium*13;

          this.iron = this.iron*19/100;

          this.ingredients = ingredients.split("-");

          for(String term : Main.entreeKeywords){
              if(ingredients.toLowerCase().contains(term)){
                  type = "entree";
                  break;
              }
          }for(String term : Main.drinkKeywords){
             if(name.toLowerCase().contains(term)){
                 type = "drink";
                 break;
             }
         }this.location = location;

          isVegetarian = booleans.charAt(0)==('t');
         isVegan = booleans.charAt(1)==('t');
         hasGluten = booleans.charAt(2)==('t');
         hasDairy = booleans.charAt(3)==('t');

     }

     public String toString(){
         return(name+","+","+serving+","+calories+","+fat+","+saturatedFat+","+cholesterol+","+sodium+","+carbs+","+
                 sugar+","+fiber+","+protein+","+calcium+","+iron);
    }




}
