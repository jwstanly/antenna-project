# antenna-project
Java code for aiming Yagi antenna from latitude, longitude, and elevation. 

The terminal based program searches for aircraft in a given latitude/longitude domain. It returns all found aircraft in the region, outputting various data points. To run the application, open the  ```OpenSkyTest.java``` and enter your OpenSky API credentials (more info below). Then compile the ```OpenSkyTest.java``` file with the classpath ```-cp``` flag in order to compile with the ```opensky.jar``` API dependency. When the application starts, clients will be prompted with the following inputs...

* minimum latitude
* maximum latitude
* mimumum longitude
* maximum longitude

After establishing the latitude/longitude domain, the console will print the total number of aircraft found. For each aircraft, the following live data points are printed...

* callsign
* ICAO24
* country of origin
* latitude
* longitude
* barometric altitude
* geometric altitude
* velocity
* ascend rate
* heading

Additionally, ```OpenSkyTest.java``` has an aggregate relationship with a ```Location.java``` object called ```homeLocation```. This latitude, longitude, elevation set is the location of the client (noticeably, ```homeLocation``` is determined at compile time, so values should be manually changed in the source code before run time). Using the location of the client, the console also prints a trajectory towards each aircraft. Each trajectory is a polar vector between the client and the aircraft. Trajectories are calculated using the ```OpenSkyTest.java```'s aggregate relationship with a ```Conversion.java``` object, which has an aggregate relationship with multiple ```Point.java``` objects. Each aircraft trajectory prints...

* azimuth
* altitude

Since each trajectory is a polar vector between the client and the aircraft, they can be used to orient any directional device towards the aircraft, such as a directional ADS-B receiver, satalite dish. More information about spherical coordinates can be found [HERE](https://en.wikipedia.org/wiki/Spherical_coordinate_system). 

Noticeably, the master branch also includes an HTTP Java server with HTML pages. Although this UI is not complete and the application should still be run through the Java main method found in ```OpenSkyTest.java```, we wanted to include this in master because this feature has potential for cloning/forking. 

Aircraft are located using OpenSky's API. OpenSky was devoloped to enable researchers to conduct experimental studies based on real data without the burden of paying for data from profit corporations and government entities. More information about the group can be found [in a paper published in 2014](https://ieeexplore.ieee.org/document/6846743) about the development and goals of OpenSky. Anyone can use OpenSky's free API if you create an account on their [website](https://opensky-network.org/). To implement an instance of their API, pass your username and password as constructor parameters to an ```OpenSkyApi``` object. As mentioned, the OpenSkyApi must run with the ```opensky.jar``` dependency at compile time.  

The algorithm for trajectories (computing the polar vector between a pair of latitude, longitude, elevation coordinates) was directly from [Don Cross](https://github.com/cosinekitty), whose work was greatly appreciated. His repo can be found [HERE](https://github.com/cosinekitty/geocalc), and a very informative article explaining his methodology can be found [HERE](https://medium.com/javascript-in-plain-english/calculating-azimuth-distance-and-altitude-from-a-pair-of-gps-locations-36b4325d8ab0). Because this project is in Java, this segment of the project could be considered a port of his JavaScript code into Java.
