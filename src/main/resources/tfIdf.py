import glob

import numpy as np
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from pymorphy2 import MorphAnalyzer

morph = MorphAnalyzer()
path = "C:/AinaArd/Crawler/src/main/resources/crawler4j/"
fileTfIdf = open('C:/AinaArd/Crawler/src/main/resources/tfIdf.txt', 'r', encoding='utf-8')
fileIndexes = open('C:/AinaArd/Crawler/src/main/resources/indexesDict.txt', 'r', encoding='utf-8')

fileLemmas = open('C:/AinaArd/Crawler/src/main/resources/lemmas.txt', 'r', encoding='utf-8')
lemmas = []
for line in fileLemmas:
    line = line.split(" ")
    lemmas.append(line[0])

fileTokens = open('C:/AinaArd/Crawler/src/main/resources/tokens.txt', 'r', encoding='utf-8')
tokens = []
for line in fileTokens:
    line = line.split(" ")
    tokens.append(line)


def filesList():
    files = []
    for i in range(101):
        for filePath in glob.glob(path + str(i) + ".txt"):
            files.append(filePath)
    return files


def remove_punctuation(data):
    symbols = "!\"#$%&()*+-./:;<=>?@[\]^_`{|}~\n"
    for i in range(len(symbols)):
        data = np.char.replace(data, symbols[i], ' ')
        data = np.char.replace(data, "  ", " ")
    data = np.char.replace(data, ',', '')
    return data


def remove_stop_words(data):
    stop_words = stopwords.words('russian')
    words = word_tokenize(str(data))
    new_text = ""
    for w in words:
        if w not in stop_words and len(w) > 2:
            new_text = new_text + " " + w
    return new_text


def remove_numbers(lines):
    lines = ''.join([i for i in lines if not i.isdigit()])
    return lines


def preprocess(filePath):
    with open(filePath, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        lines = remove_punctuation(lines)
        lines = remove_stop_words(lines)
        lines = remove_numbers(lines)
    return lines


def writeResultToFile(file, result, filePath):
    file.write(filePath + '\n')
    for key in result.keys():
        file.write(key + ': ')
        for value in result[key]:
            file.write(str(value) + ', ')
        file.write('\n')


def removeDuplicates(dict):
    for key in dict.keys():
        dict[key] = list(dict.fromkeys(dict[key]))


def processFile(filePath):
    dict = {}
    wordsCount = 0

    lines = preprocess(filePath)
    words = lines.split(" ")
    for word in words:
        if len(word) > 2 and word != ' ':
            wordsCount += 1
            word = morph.normal_forms(word)[0]
            if word not in dict:
                dict[word] = 1
            else:
                dict[word] += 1
    return dict, wordsCount


def createIndexes():
    indexes = {}

    for tokenStr in tokens:
        newToken = morph.normal_forms(tokenStr[0])[0]
        tokenStr[0] = newToken

        if len(tokenStr[0]) > 2:
            if tokenStr[0] not in indexes:
                indexes[tokenStr[0]] = []
                number = tokenStr[1].replace('\n', '')
                indexes[tokenStr[0]].append(number)
            else:
                number = tokenStr[1].replace('\n', '')
                indexes[tokenStr[0]].append(number)

    return indexes


def readIndexes():
    indexes = []
    for line in fileIndexes:
        line = line.split(" ")
        indexes.append(line)
    return indexes


def findKeyNumber(key):
    count = 0

    for n in indexes.keys():
        indexes[n] = list(indexes.fromkeys(indexes[n]))
        if n == key:
            count = len(indexes[key])

    return count


def tf_idf(dict, wordsCount, filePath):
    result = {}
    for key in dict.keys():
        result[key] = []
        tf = dict[key] / wordsCount

        count = findKeyNumber(key)
        if count != 0:
            idf = 100 / count
            result[key].append(idf)

            tf_idf = tf * idf
            result[key].append(tf_idf)

    print(result)
    writeResultToFile(fileTfIdf, result, filePath)


def countTfIdf(files):
    for filePath in files:
        dict, wordsCount = processFile(filePath)
        tf_idf(dict, wordsCount, filePath)


def calculate():
    files = filesList()
    countTfIdf(files)


indexes = createIndexes()
removeDuplicates(indexes)
calculate()
