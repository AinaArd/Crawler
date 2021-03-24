from pymorphy2 import MorphAnalyzer

path = "C:/AinaArd/Crawler/src/main/resources/crawler4j/"
dict = {}
morph = MorphAnalyzer()
search = "воспоминанию делаем блины"

fileTokens = open('C:/AinaArd/Crawler/src/main/resources/tokens.txt', 'r', encoding='utf-8')
tokens = []
for line in fileTokens:
    line = line.split(" ")
    tokens.append(line)


def formalizeSearch(search):
    result = []
    morph = MorphAnalyzer()
    words = search.split(" ")
    for word in words:
        elem = morph.normal_forms(word)[0]
        result.append(elem)
    return result


def searchAllLemmaApperances(search, tokens):
    for elem in search:
        for tokenStr in tokens:
            token = morph.normal_forms(tokenStr[0])[0]
            if token == elem:
                if token not in dict:
                    dict[elem] = []
                    number = tokenStr[1].replace('\n', '')
                    dict[elem].append(number)
                else:
                    number = tokenStr[1].replace('\n', '')
                    dict[elem].append(number)
            print(token)


def removeDuplicates():
    for key in dict.keys():
        dict[key] = list(dict.fromkeys(dict[key]))


def boolSearch():
    result = []
    temp = list(dict)
    size = len(dict)
    for key in dict.keys():
        if temp.index(key) < size-1:
            nextKey = temp[temp.index(key) + 1]
            result.append(set(dict[key]).intersection(dict[nextKey]))
    return result

result = formalizeSearch(search)
searchAllLemmaApperances(result, tokens)
removeDuplicates()
boolSearch()

print(dict)
