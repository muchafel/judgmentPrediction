#
# Copyright 2017
# Ubiquitous Knowledge Processing (UKP) Lab
# Technische Universität Darmstadt
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program. If not, see http://www.gnu.org/licenses/.


from __future__ import print_function

from sys import argv
from keras.preprocessing import sequence
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation
from keras.layers import Embedding, TimeDistributed, Bidirectional, Flatten,Convolution1D, MaxPooling1D, GlobalMaxPooling1D
from keras.layers import Embedding
from keras.layers import LSTM
from keras.layers import Conv1D, GlobalMaxPooling1D
from keras.datasets import imdb
import numpy as np

np.random.seed(743)  # reproducibility

def numpyizeDataVector(vec):
    trainVecNump=[]
    file = open(vec, 'r')
    for l in file.readlines():
        l = l.strip()
        trainVecNump.append(np.fromstring(l, dtype=float, sep=' '))
    file.close()
    return trainVecNump

def numpyizeOutcomeVector(vec):
    file = open(vec, 'r')
    v=""
    for l in file.readlines():
        l = l.strip()
        v=np.fromstring(l, dtype=float, sep=' ')
        print(v)
    file.close()
    return v

def scoreTransform(v):
    out=""
    min = np.min(v)
    max = np.max(v)
    for e in v:
        val = ((e - min) / (max - min))
        out+= str(val) + " "
    return np.fromstring(out.strip(), dtype=float, sep=' '), max, min

def transformBack(v, max, min):
    out=[]
    for e in v:
        val = e * (max-min)+min
        out.append(val)
    return out

def loadEmbeddings(emb):
    f = open(emb, 'r')
    embData = f.readlines()
    f.close()
    dim = len(embData[0].split())-1
    matrix = np.zeros((len(embData)+1, dim))
    for e in embData:
        e = e.strip()
        if not e:
            continue
        idx = e.find(" ")
        id = e[:idx]
        vector = e[idx+1:]
        matrix[int(id)]=np.asarray(vector.split(" "), dtype='float32')
    return matrix, dim

def runExperiment(trainVec, trainOutcome, testVec, testOutcome, embedding, maximumLength, predictionOut):
    
    trainVecNump = numpyizeDataVector(trainVec)
    trainOutcome = numpyizeOutcomeVector(trainOutcome)
    print(trainOutcome)

    testVecNump = numpyizeDataVector(testVec)
    testOutcome = numpyizeOutcomeVector(testOutcome)

    x_train = sequence.pad_sequences(trainVecNump, maxlen=int(maximumLength))
    x_test = sequence.pad_sequences(testVecNump, maxlen=int(maximumLength))

    y_train = trainOutcome
    y_test = testOutcome
    
    print(x_train)
    print(y_train)

    embeddings, dim = loadEmbeddings(embedding)

    model = Sequential()
    model.add(Embedding(output_dim=embeddings.shape[1], input_dim=embeddings.shape[0], input_length=x_train.shape[1], weights=[embeddings], trainable=False))
    model.add(Dropout(0.5))
    model.add(Conv1D(250,3,padding='valid',activation='relu',strides=1))
    model.add(GlobalMaxPooling1D())
    model.add(Dense(50, kernel_initializer='normal', activation='relu'))
    model.add(Dense(10, kernel_initializer='normal', activation='relu'))
    model.add(Dense(1, kernel_initializer='normal'))
    model.compile(loss='mean_squared_error', optimizer='adam')
    model.fit(x_train, y_train, epochs=20, shuffle=True)

    prediction = model.predict(x_test)

    predictionFile = open(predictionOut, 'w')
    predictionFile.write("#Gold\tPrediction\n")
    for i in range(0, len(prediction)):
        predictionFile.write(str(y_test[i]) +"\t" + str(prediction[i][0])+ "\n")
    predictionFile.close()


if  __name__ =='__main__':
    runExperiment(argv[1], argv[2], argv[3], argv[4], argv[5], argv[6], argv[7])