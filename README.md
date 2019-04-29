# Municipal Solid Waste Management System
A software project for managing collection of municipal solid waste (MSW), 
created during my second year of B. Tech. 
It is made using Kotlin, TornadoFX and H2 Database.

### Features
- Tabular view for location, vehicles and collection data
- Forms for easy data modification
- Charts displaying collection per day and collection per location type
- Report generation in PDF
- Inbuilt SQL editor for testing 

### Screenshots
Collection table and form
![Screenshot 1](screenshots/shot-1.png?raw=true)
Overview tab displaying the charts
![Screenshot 2](screenshots/shot-2.png?raw=true)

### Building
JDK 8 is required to build. 
Execute the following commands to build:
```sh
$ ./gradlew distZip
```
Then the app can be started by using:
```sh
$ cd build/distributions
$ unzip mswms-0.1.zip
$ ./mswms-0.1/bin/mswms.sh
```

### License
This project is licensed under the MIT License. 
Please see the [LICENSE](LICENSE) file for more information.

### Credits
[easytable](https://github.com/vandeseer/easytable) - Generates awesome tables in PDF <br>
[jfxtras](https://github.com/JFXtras/jfxtras) - Additional utilities for JavaFX