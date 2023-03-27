import requests
import json
url = "http://localhost:8080/api/products/23"

payload=json.dumps({"name":"Samsung jfghjA12122","price":1600165465420.0,"category":"Electronics","rating":4,"quantity":100000,"description":"212331Books"})
payload={}
headers = {
  'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjoxLCJzdWIiOiJkYXZlamVuIiwiaWF0IjoxNjc3MTE2OTA4LCJleHAiOjE2NzcxNTI5MDh9.MGpMAEV8dBdoIqzsnYfRPcvJ4K4JYvwqzlbWaXh23zQ',
  'Accept':"application/json",
  'Content-type':'application/json'
}

response = requests.request("GET", url, headers=headers, data=payload,)

print(response.text,response.status_code)