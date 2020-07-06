# antenna-project
Java code for aiming Yagi antenna from latitude, longitude, and elevation. 

The terminal based program searches for aircraft in a given latitude/longitude domain. It returns all found aircraft in the region, outputting various data points. To run the application, open the  ```OpenSkyTest.java``` and enter your OpenSky API credentials (more info below). Then compile the ```OpenSkyTest.java``` file with the classpath ```-cp``` flag in order to compile with the ```opensky.jar``` API dependency. When the application starts, clients will be prompted with the following inputs...

* minimum latitude
* maximum latitude
* mimumum longitude
* maximum longitude

After establishing the latitude/longitude domain, the console will print the total number of aircraft found. For each aircraft, the following live data points are printed...

* callsign
* ICA024
* country of origin
* latitude
* longitude
* barometric altitude
* geometric altitude
* velocity
* ascend rate
* heading

Additionally, ```OpenSkyTest.java``` has an aggregate relationship with a ```Location.java``` object called ```homeLocation```. This latitude, longitude, elevation set is the location of the client (noticeably, ```homeLocation``` is determined at compile time, so values should be manually changed in the source code before run time). Using the location of the client, the console also prints a trajectory towards each aircraft using a ```Conversion.java``` object. This trajectory prints...

* azimuth
* altitude

These values can be used to orient any directional device towards the aircraft, such as a Yagi directional antenna. More information about spherical coordinates can be found at https://en.wikipedia.org/wiki/Spherical_coordinate_system. 

Aircraft are located using OpenSky's API. Anyone can use OpenSky's free API if you create an account on their website (https://opensky-network.org/). To implement an instance of their API, pass your username and password as constructor parameters to an ```OpenSkyApi``` object. 

The algorithm for trajectories (computing the polar vector between a pair of latitude, longitude, elevation coordinates) was taken directly from Don Cross, whose work was greatly appreciated. The source can be found at https://github.com/cosinekitty/geocalc, and a very informative article explaining his methodology can be found at https://medium.com/javascript-in-plain-english/calculating-azimuth-distance-and-altitude-from-a-pair-of-gps-locations-36b4325d8ab0.
Because this project is in Java, this segment could be considered a port of his JavaScript code into Java.
