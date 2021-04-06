# umt-internship

The implementation of this probelm is based on the merge method from the merge sort algorithm. It should cover all cases considering the two input calendars are correctly given and "sorted".

Notes: 
-I considered 30 min to be 0.5f for simplicity so for example 12:30 would be 12.5f.
-I also have to mention that i think the sample output provided is incorrect, namely the firsttime interval "[11:30, 12:30]". Shouldn't it be "[11:30, 12:00]" since the first person has an appointment from '12:00' to '13:00'?. In my implementation "[11:30, 12:00]" is the considered free time interval.