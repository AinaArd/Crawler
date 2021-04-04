from pymorphy2 import MorphAnalyzer

morph = MorphAnalyzer()
path = 'C:/AinaArd/Crawler/src/main/resources/transition.txt'

file = open(path, 'r+', encoding='utf-8')
word = file.readlines()


def lemmatize(word):
    lemma = []
    for w in word:
        lemma = morph.normal_forms(w)[0]
    return lemma


result = lemmatize(word)
f = open(path, 'w', encoding='utf-8')
f.seek(0)
f.write(result)
f.close()