# mmgestor

This application was generated using JHipster 6.6.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.6.0](https://www.jhipster.tech/documentation-archive/v6.6.0).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### Security

https://volcanohong.github.io/2016/11/18/jhipster-project/

### Update Entities

```
jhipster import-jdl .\mmgestor.jh --json-only
jhipster entity EntityName --regenerate
```

```
mvn compile liquibase:diff
mvn liquibase:clearCheckSums
```

Add the "new_changelog.xml" in the master.xml

### Tools

https://github.com/brunoc107/ngx-viacep
https://csp-evaluator.withgoogle.com/
https://report-uri.com/home/hash
https://fontawesome.com/icons?d=gallery

### Troubleshooting

https://stackoverflow.com/questions/41019034/check-sum-error-in-jhipster-when-editing-an-existing-entity-generated-using-the

```
./mvnw liquibase:clearCheckSums
UPDATE databasechangelog SET md5sum = null
```

https://stackoverflow.com/questions/34655157/how-to-solve-liquibase-checksum-validation-fail-after-liquibase-upgrade/34661256

https://stackoverflow.com/questions/22691455/jhipster-liquibase-doesnt-update-database

https://www.jhipster.tech/development/#database-updates

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    npm install --save --save-exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    npm install --save-dev --save-exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

    ng generate component my-component

will generate few files:

    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts

## Building for production

### Packaging as jar

To build the final jar and optimize the mmgestor application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:800](http://localhost:800) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

For more information, refer to the [Running tests page][].

### Code quality

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.6.0 archive]: https://www.jhipster.tech/documentation-archive/v6.6.0
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.6.0/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.6.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.6.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.6.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.6.0/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.6.0/setting-up-ci/
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
