import glob

path = "C:/AinaArd/Crawler/src/main/resources/crawler4j/"
dict = {}

fileLemmas = open('C:/AinaArd/Crawler/src/main/resources/lemmas.txt', 'r', encoding='utf-8')
lemmas = []

for line in fileLemmas:
    line = line.split(" ")
    lemmas.append(line[0])


def countAllLemmaApperances(filePath, lemma, i):
    file = open(filePath, encoding='utf-8')
    for line in file:
        if line.count(lemma):
            if lemma not in dict:
                dict[lemma] = []
            else:
                dict[lemma].append(i)


def getAllFiles():
    for i in range(101):
        for filePath in glob.glob(path + str(i) + ".txt"):
            for lemma in lemmas:
                countAllLemmaApperances(filePath, lemma, i)
                print(i)


getAllFiles()
print(dict)
