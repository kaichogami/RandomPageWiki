"""Opens up a random page to read using wikiapi in default browser"""

import requests
import webbrowser

def get_random():
    random = requests.get("https://en.wikipedia.org/w/api.php?action=query&list=random&rnlimit=20&format=json")

    return random.json()['query']['random']

def choose(random_list):
    for x in xrange(10):
        choice = raw_input(("Do you want to read about {0}").format(random_list[x]['title'].encode('ascii', 'ignore')))
        #skip foreign unicode characters

        if choice == 'bye':
            exit(0)

        if choice == 'y':
            return random_list[x]['id']
            exit(0)

    choose(get_random())

def browser_open(idd):
    #id is a keyword

    main = 'https://en.wikipedia.org/wiki?curid='
    return webbrowser.open(main+str(idd))
            
if __name__ == '__main__':
    print("Generating random page list will take 2-3 seconds depending on internet speed.")
    print("n = no, bye=exit, anything else = yes")
    browser_open(choose(get_random()))    
    
