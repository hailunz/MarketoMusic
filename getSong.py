
# Given artist name, find the songs
# Utilize spotify
# Usage: cat input | python getSong.py > output.txt

# Author : Hailun Zhu

import sys
import urllib2
import json


url_search = 'https://api.spotify.com/v1/search?'
type='&type=artist'

# find the artist's id by name
def search_id(name):

    urlsend=url_search+'q='+name+type;
    request = urllib2.Request(urlsend)
    request.add_header("Content-Type","application/json")
    request.add_method = lambda:'GET'
    result = urllib2.urlopen(request);

    res =json.loads(result.read())
    return res['artists']['items'][0]['id'];

def find_tracks(name,id):
    url="https://api.spotify.com/v1/artists/" + id+"/top-tracks?country=ES";
    request = urllib2.Request(url)
    request.add_header("Content-Type","application/json")
    request.add_method = lambda:'GET'
    result = urllib2.urlopen(request);
    res =json.loads(result.read())
    name = name.replace("%20"," ")
    for a in res['tracks']:
        print name + "    "+a['name']

# main function
if __name__ == '__main__':
    for line in sys.stdin:
        name = line.split("\n")[0]
        name = name.replace(" ","%20")
        id = search_id(name)
        find_tracks(name,id);

