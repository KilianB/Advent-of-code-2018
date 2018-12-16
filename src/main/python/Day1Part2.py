from _io import open

file = open("../resources/inputDay1Part2.txt","r")

numbers = [];

for x in file:
    numbers.append(int(x));

print(numbers)
i = 0
frequency = 0
known = set()

while True:
    frequency += numbers[i]
    i = i+1
    if i >= len(numbers) :
        i = 0
    if frequency in known:
        break
    known.add(frequency)
    
print(frequency)