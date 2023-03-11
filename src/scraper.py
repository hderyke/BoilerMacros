from urllib.request import Request

import requests
from bs4 import BeautifulSoup as soup
from urllib.request import Request, urlopen
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

def check_for_less_than(s):
    if s[0] == '<':
         s = "0"
    return s


def format_str(calories,info):
    calories_int = calories[0:calories.index('a')]
    print(calories_int)
    item_str = calories[calories.index('a') + 10:len(calories)]
    serving = item_str[1:item_str.index("Calories")]
    fat = info[info.index("fat")+3:info.index('g')]
    info = info[info.index("Sat"):len(info)]
    sat_fat = info[info.index("ted fat")+7:info.index("g")]
    info = info[(info.index("terol")+5):len(info)]
    cholesterol = info[0:info.index("mg")]
    info = info[(info.index("ium") + 3):len(info)]
    sodium = info[0:info.index("mg")]
    info = info[(info.index("rate") + 4):len(info)]
    carbs = info[0:info.index("g")]
    info = info[(info.index("Sugar") + 5):len(info)]
    sugar = info[0:info.index("g")]
    print(sugar[0:1])
    if (sugar[0:1]) == "A":
        sugar = "0"
    info = info[(info.index("iber") + 4):len(info)]
    fiber = info[0:info.index("g")]
    info = info[(info.index("tein") + 4):len(info)]
    protein = info[0:info.index("g")]
    info = info[(info.index("cium") + 4):len(info)]
    calcium = info[0:info.index("I")]
    info = info[(info.index("Iron") + 4):len(info)]
    iron = info

    print("calories "+calories_int)
    print("serving size "+serving)
    if serving[1] == '/':
        x = int(serving[0])
        y = int(serving[2])
        serving = str(round(x / y)) + serving[3:len(serving)]

    fat = check_for_less_than(fat)
    print("fat "+fat)
    print("satfat " + sat_fat)
    sat_fat = check_for_less_than(sat_fat)
    print("cholesterol "+cholesterol)
    cholesterol = check_for_less_than(cholesterol)
    print("sodium "+sodium)
    sodium = check_for_less_than(sodium)
    print("carbs " + carbs)
    carbs = check_for_less_than(carbs)
    print("sugar " + sugar)
    sugar = check_for_less_than(sugar)
    print("fiber" + fiber)
    fiber = check_for_less_than(fiber)
    print("protein "+ protein)
    protein = check_for_less_than(protein)
    print("calcium " + calcium)
    calcium= check_for_less_than(calcium)
    print("iron " + iron)
    iron = check_for_less_than(iron)

    item_str = ("," + serving + "," + calories_int + "," + fat + "," + sat_fat + "," + cholesterol + "," + sodium + "," + carbs + "," + sugar + "," + fiber + "," + protein + "," + calcium + "," + iron + ",fffff,")
    print(item_str)
    return item_str



def main():
    file = open("foods.txt","a")
    driver = webdriver.Safari()
    driver.get("https://dining.purdue.edu/menus/Ford/2023/3/20/Lunch")
    driver.back()
    elem = driver.find_elements(By.CLASS_NAME,"station-item")
    c = 0;

    for e in elem:
        driver.find_element(By.CLASS_NAME,"meal").click()
        name = e.text[0:e.text.index("keyboard")]
        for i in name:
            if i.isnumeric():
                name = name[0:name.index(i)]
                break

        print(name)
        print(e.get_attribute('href'))
        if("Allergens Unavailable" in e.text):
           # file.write(name[0:name.index("Allergens")])
            print("No info avalible\n\n")
            driver.implicitly_wait(50)
            c = c+1
            continue
        file.write(name)
        item = elem[c]
        driver.get(item.get_attribute("href"))
        driver.implicitly_wait(50)
        item = driver.find_element(By.CLASS_NAME, "nutrition-table")
        print("CALS", driver.find_element(By.CLASS_NAME, "nutrition-feature-calories").text)
        if("CholesterolSodium" in item.text):
            print("No info avalible\n\n")
            c = c + 1
            driver.back()
            driver.back()
            continue

        formatted = format_str(driver.find_element(By.CLASS_NAME, "nutrition-feature-calories").text,item.text)
        print("INFO", item.text)
        file.write(formatted)
        if ("CholesterolSodium" in item.text):
            print("No info avalible 2\n\n")
            continue
        else:
            ing_str = str(driver.find_element(By.CLASS_NAME, "nutrition-ingredient-list").text)
            ing_str = ing_str.replace(", ",'-')
            try:
                print("index of comma "+ing_str.index(","))
            except:
                print("index -1")
            print(ing_str)
            file.write(ing_str)
            file.write(",Ford-idk-l")


        file.write("\n")
        print("\n")
        print("\n")
        driver.back()
        driver.back()
        c = c+1


    assert "No results found." not in driver.page_source
    file.close()
    driver.close()

    return 0;




if __name__ == '__main__':
    main()


