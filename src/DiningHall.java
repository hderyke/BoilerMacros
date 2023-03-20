import java.util.ArrayList;

public class DiningHall {

    String name;


    ArrayList<Item> entrees = new ArrayList<>();
    ArrayList<Item> sides = new ArrayList<>();
    ArrayList<Item> drinks = new ArrayList<>();
    ArrayList<Item> desserts = new ArrayList<>();

    DiningHall(String name){
        this.name = name;
        for(Item item: Main.items){
            if(item.location.contains(name)){
                if(item.location.contains("Delectables") || item.location.contains("Cookie Cache") || item.location.contains("Sugar Hill") || item.location.contains("Devonshire Way") || item.name.contains("Muffin")){
                    item.type = "dessert";
                }
                switch(item.type){
                    case "entree":
                        entrees.add(item);
                        continue;
                    case "side":
                        sides.add(item);
                        continue;
                    case "drink":
                        drinks.add(item);
                        continue;
                    case "dessert":
                        desserts.add(item);
                }
            }
        }
    }




}
