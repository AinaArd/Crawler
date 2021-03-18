from pymorphy2 import MorphAnalyzer
from nltk.corpus import stopwords

stopwords_ru = stopwords.words("russian")
morph = MorphAnalyzer()

file1 = open('C:/AinaArd/Crawler/src/main/resources/lemmas.txt', 'w', encoding='utf-8')

with open('C:/AinaArd/Crawler/src/main/resources/tokens.txt', 'r', encoding='utf-8') as file:
    tokens = file.readlines()

def writeToFile(lemmasMap):
    for key in lemmasMap.keys():
        file1.write(key + ' ')
        for value in lemmasMap[key]:
            file1.write(value + ' ')
        file1.write('\n')


def lemmatize():
    lemmasMap = {}
    for token in tokens:
        token = token.replace('\n', '')
        if token not in stopwords_ru:
            tokenStrip = token.strip()
            lemma = morph.normal_forms(tokenStrip)[0]
            if not bool(lemmasMap.get(lemma)):
                lemmasMap[lemma] = []
            lemmasMap[lemma].append(tokenStrip)

    return lemmasMap

lemmasMap = lemmatize()
writeToFile(lemmasMap)