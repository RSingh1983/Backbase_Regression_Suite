**Backbase automation framework**

This framework is written using Serenity BDD with Cucumber JVM.

**The project structure is shown below:**

+ **main**: Contains all the utility and basic functions which are generic and can be used across projects.
+ **test**: Contains all the tests and their corresponding implementations.


**Steps to run:**
+ mvn clean install -Denv=live -Dcucumber.options="--tags @regression"

where **regression** is the tag of the features which need to be run and **live** is the name of the property file specifying the properties specific to a test environment.
Note: By default the browser specified in chrome and the same can be configured using the "serenity.properties" file. Also, path of the chrome driver is specified in the "serenity.properties" file, which need to be updated as per the path of the driver.
