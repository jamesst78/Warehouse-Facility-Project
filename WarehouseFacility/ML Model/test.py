#import pandas
import pandas as pd
import numpy as np
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler



wine = pd.read_csv("wine.data", names=["class", "alcohol", "malic_acid", "ash", "alcalinity_of_ash", "magnesium", "total_phenols",
                                          "flavanoids", "nonflavanoid_phenols", "proanthocyanins", "color_intensity", "hue", 
                                           "od280_od315_of_diluted_wines", "proline"])
                                           


print(wine.head());


X = wine.drop(["class"], axis=1)
y = wine["class"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=123)

scaler = StandardScaler()
train_scaled = scaler.fit_transform(X_train)
test_scaled = scaler.transform(X_test)

model = MLPClassifier()
print(model.fit(train_scaled, y_train))


#print(X_train.shape);
#print(X_test.shape);
print(accuracy_score(y_test, model.predict(test_scaled)));
