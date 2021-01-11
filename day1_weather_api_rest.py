#import statement
import requests

#Set the API endpoint

url = "https://www.metaweather.com/api/location/search/?query=delhi"

#Library to perform HTTP GET requests to the url
res = requests.get(url).json()

other_url = "https://www.metaweather.com/api/location/" + str(res[0]['woeid']) + "/"

other_res = requests.get(other_url).json()

#Print the weather for current date in Delhi

print("The weather in " + other_res['title'] + " on " + other_res['consolidated_weather'][0]['applicable_date'] + " is " +other_res['consolidated_weather'][0]['weather_state_name'])
