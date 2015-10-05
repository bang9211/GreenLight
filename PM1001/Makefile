SName = PM1001
SensorC = $(SName).c
SensorO = $(SName).o
SensorLib = lib$(SName).so

$(SensorLib):$(Sensor0)
	#make library file
	gcc -shared -W1 -o $(SensorLib) $(SensorO) -L /MinT/usr/BBBio -lBBBio -I ${JAVA_HOME}/include -I ${JAVA_HOME}/include/linux
	#Copy library file to MinT driver folder
	cp $(SensorLib) /MinT/driver/
$(Sensor0):$(SensorC)
	gcc -c -fPIC $(SensorC) -I ${JAVA_HOME}/include -I ${JAVA_HOME}/include/linux
clean:
	rm -rf $(SensorO)