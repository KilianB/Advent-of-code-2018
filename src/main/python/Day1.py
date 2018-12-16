from _io import open

file = open("../resources/inputDay1.txt", "r");

frequency = 0;
for x in file:
    frequency += int(x);

print(frequency)