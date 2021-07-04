#import pandas
import pandas as pd
import numpy as np
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler



CodeAnalyser = pd.read_csv("vecs.data", names=["class","GoodCommitMessage","StressTested","CollapsibleIfStatements","LongVariable","ForLoopVariableCount",
"UnusedLocalVariable","AddEmptyString","CyclomaticComplexity","AvoidDeeplyNestedIfStmts","UnusedAssignment","MethodArgumentCouldBeFinal",
"AvoidInstantiatingObjectsInLoops","PrematureDeclaration","ShortVariable","ControlStatementBraces","UnusedFormalParameter","LocalVariableCouldBeFinal"]);
                                           


print(CodeAnalyser.head());


X = CodeAnalyser.drop(["class"], axis=1)
y = CodeAnalyser["class"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=123)

scaler = StandardScaler()
train_scaled = scaler.fit_transform(X_train)
test_scaled = scaler.transform(X_test)

model = MLPClassifier()
print(model.fit(train_scaled, y_train))


#print(X_train.shape);
#print(X_test.shape);
print(accuracy_score(y_test, model.predict(test_scaled)));
